/**
 * 
 */
package com.francisco5em.springrestmvc.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
	
	

	@Override
	public List<BeerDTO> listBeers() {
		// TODO Auto-generated method stub
		return beerRepo.findAll().stream().map(beerMap::beerToBeerDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Optional<BeerDTO> getBeerById(UUID id) {
		// TODO Auto-generated method stub
		return Optional
				.ofNullable(beerMap.beerToBeerDTO(beerRepo.findById(id).orElse(null)));
	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beer) {
		// TODO Auto-generated method stub
		return beerMap.beerToBeerDTO(beerRepo.save(beerMap.beerDTOtoBeer(beer)));
	}

	@Override
	public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
		// TODO Auto-generated method stub
		AtomicReference<Optional<BeerDTO>> atomicBeer = new AtomicReference<Optional<BeerDTO>>();
		beerRepo.findById(beerId).ifPresentOrElse(foundBeer -> {
			foundBeer.setBeerName(beer.getBeerName());
			foundBeer.setBeerStyle(beer.getBeerStyle());
			foundBeer.setUpc(beer.getUpc());
			foundBeer.setPrice(beer.getPrice());
			atomicBeer.set(Optional.of(
					beerMap.beerToBeerDTO(beerRepo.save(foundBeer))));

		}, () -> {
			atomicBeer.set(Optional.empty());
		});
		return atomicBeer.get();
	}

	@Override
	public Boolean deleteById(UUID beerId) {
		// TODO Auto-generated method stub
		if (beerRepo.existsById(beerId)) {
			beerRepo.deleteById(beerId);
			return true;
		}else {return false;}
		
		

	}

	@Override
	public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
		// TODO Auto-generated method stub
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
			
			atomicBeer.set(Optional.of(
					beerMap.beerToBeerDTO(beerRepo.save(foundBeer))));

		}, () -> {
			atomicBeer.set(Optional.empty());
		});
		return atomicBeer.get();
		
	}
}
