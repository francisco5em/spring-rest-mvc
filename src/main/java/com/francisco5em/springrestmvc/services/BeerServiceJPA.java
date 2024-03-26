/**
 * 
 */
package com.francisco5em.springrestmvc.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.francisco5em.springrestmvc.entities.Beer;
import com.francisco5em.springrestmvc.mappers.BeerMapper;
import com.francisco5em.springrestmvc.model.BeerDTO;
import com.francisco5em.springrestmvc.model.BeerStyle;
import com.francisco5em.springrestmvc.repositories.BeerRepository;

import lombok.RequiredArgsConstructor;

/**
 * Creado por Francisco E.
 */
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

	private final BeerRepository beerRepo;
	private final BeerMapper beerMap;

	private static final int DEFAULT_PAGE = 0;
	private static final int DEFAULT_PAGE_SIZE = 25;

	@Override
	public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle,
			Boolean showInventory, Integer pageNumber, Integer pageSize) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

		Page<Beer> beerPage;

		if (StringUtils.hasText(beerName) && beerStyle == null) {
			beerPage = listBeersByName(beerName, pageRequest);
		} else if (!StringUtils.hasText(beerName) && beerStyle != null) {
			beerPage = listBeersByStyle(beerStyle, pageRequest);
		} else if (StringUtils.hasText(beerName) && beerStyle != null) {
			beerPage = listBeersByNameAndStyle(beerName, beerStyle, pageRequest);
		} else {

			beerPage = beerRepo.findAll(pageRequest);
		}

		if (showInventory != null && !showInventory) {
			beerPage.forEach(beer -> beer.setQuantityOnHand(null));
		}
		return beerPage.map(beerMap::beerToBeerDTO);

		// return
		// beerPage.stream().map(beerMap::beerToBeerDTO).collect(Collectors.toList());
	}

	public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
		int queryPageNumber;
		int queryPageSize;

		if (pageNumber != null && pageNumber > 0) {
			queryPageNumber = pageNumber - 1;
		} else {
			queryPageNumber = DEFAULT_PAGE;
		}

		if (pageSize == null) {
			queryPageSize = DEFAULT_PAGE_SIZE;
		} else {
			if (pageSize > 1000) {
				queryPageSize = 1000;
			} else {
				queryPageSize = pageSize;
			}
		}
		
		Sort sort = Sort.by(Sort.Order.asc("beerName"));

		return PageRequest.of(queryPageNumber, queryPageSize,sort);
	}

	private Page<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle,
			Pageable pageable) {
		return beerRepo.findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(
				"%" + beerName + "%", beerStyle, pageable);
	}

	public Page<Beer> listBeersByStyle(BeerStyle beerStyle, Pageable pageable) {
		return beerRepo.findAllByBeerStyle(beerStyle, pageable);
	}

	public Page<Beer> listBeersByName(String beerName, Pageable pageable) {
		return beerRepo.findAllByBeerNameContainsIgnoreCase(beerName, pageable);
	}

	@Override
	public Optional<BeerDTO> getBeerById(UUID id) {

		return Optional
				.ofNullable(beerMap.beerToBeerDTO(beerRepo.findById(id).orElse(null)));
	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beer) {

		return beerMap.beerToBeerDTO(beerRepo.save(beerMap.beerDTOtoBeer(beer)));
	}

	@Override
	public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {

		AtomicReference<Optional<BeerDTO>> atomicBeer = new AtomicReference<Optional<BeerDTO>>();
		beerRepo.findById(beerId).ifPresentOrElse(foundBeer -> {
			foundBeer.setBeerName(beer.getBeerName());
			foundBeer.setBeerStyle(beer.getBeerStyle());
			foundBeer.setUpc(beer.getUpc());
			foundBeer.setPrice(beer.getPrice());
			atomicBeer.set(Optional.of(beerMap.beerToBeerDTO(beerRepo.save(foundBeer))));

		}, () -> {
			atomicBeer.set(Optional.empty());
		});
		return atomicBeer.get();
	}

	@Override
	public Boolean deleteById(UUID beerId) {

		if (beerRepo.existsById(beerId)) {
			beerRepo.deleteById(beerId);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {

		AtomicReference<Optional<BeerDTO>> atomicBeer = new AtomicReference<Optional<BeerDTO>>();
		beerRepo.findById(beerId).ifPresentOrElse(foundBeer -> {
			if (StringUtils.hasText(beer.getBeerName())) {
				foundBeer.setBeerName(beer.getBeerName());
			}

			if (beer.getBeerStyle() != null) {
				foundBeer.setBeerStyle(beer.getBeerStyle());
			}

			if (beer.getPrice() != null) {
				foundBeer.setPrice(beer.getPrice());
			}

			if (beer.getQuantityOnHand() != null) {
				foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
			}

			if (StringUtils.hasText(beer.getUpc())) {
				foundBeer.setUpc(beer.getUpc());
			}

			atomicBeer.set(Optional.of(beerMap.beerToBeerDTO(beerRepo.save(foundBeer))));

		}, () -> {
			atomicBeer.set(Optional.empty());
		});
		return atomicBeer.get();

	}
}
