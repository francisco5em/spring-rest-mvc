/**
 * 
 */
package com.francisco5em.springrestmvc.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.francisco5em.springrestmvc.entities.Beer;
import com.francisco5em.springrestmvc.mappers.BeerMapper;
import com.francisco5em.springrestmvc.model.*;

import lombok.extern.slf4j.Slf4j;

/**
 * Creado por Francisco E.
 */
@Slf4j
@Service
public class BeerServiceImple implements BeerService {
	private Map<UUID, BeerDTO> beerMapa;

	public BeerServiceImple() {
		this.beerMapa = new HashMap<>();

		BeerDTO beer1 = BeerDTO.builder().id(UUID.randomUUID()).version(1)
				.beerName("Sonora Especial").beerStyle(BeerStyle.WHEAT).upc("1151533")
				.price(new BigDecimal("40.00")).quantityOnHand(122)
				.createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();

		BeerDTO beer2 = BeerDTO.builder().id(UUID.randomUUID()).version(1)
				.beerName("Bud-Heavy").beerStyle(BeerStyle.PALE_ALE).upc("12322")
				.price(new BigDecimal("11.99")).quantityOnHand(392)
				.createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();

		BeerDTO beer3 = BeerDTO.builder().id(UUID.randomUUID()).version(1)
				.beerName("White Tower").beerStyle(BeerStyle.IPA).upc("1252356")
				.price(new BigDecimal("13.99")).quantityOnHand(144)
				.createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now()).build();
		log.debug("constructor - in BeerServiceImple - 3 Beers created");

		beerMapa.put(beer1.getId(), beer1);
		beerMapa.put(beer2.getId(), beer2);
		beerMapa.put(beer3.getId(), beer3);

		log.debug("BeerServiceImple() - in BeerServiceImple - 3 Beers added to Hashmap");

	}

	@Override
	public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle,
			Boolean showInventory, Integer pageNumber, Integer pageSize) {

		log.debug("listBeers() - in BeerServiceImple. beerName: " + beerName
				+ " - beerStyle:" + beerStyle);

		return new PageImpl<BeerDTO>(new ArrayList<BeerDTO>(beerMapa.values()));

	}

	@Override
	public Optional<BeerDTO> getBeerById(UUID id) {

		log.debug("getBeerById(UUID id) - in BeerServiceImple. Id: " + id.toString());

		return Optional.ofNullable(beerMapa.get(id));
	}

	@Override
	public BeerDTO saveNewBeer(BeerDTO beer) {

		BeerDTO savedBeer = BeerDTO.builder().id(UUID.randomUUID())
				.createdDate(LocalDateTime.now()).version(beer.getVersion())
				.updateDate(LocalDateTime.now()).beerName(beer.getBeerName())
				.beerStyle(beer.getBeerStyle()).quantityOnHand(beer.getQuantityOnHand())
				.upc(beer.getUpc()).price(beer.getPrice()).build();

		beerMapa.put(savedBeer.getId(), savedBeer);

		return savedBeer;
	}

	@Override
	public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
		// TODO Auto-generated method stub
		BeerDTO existing = null;
		existing = beerMapa.get(beerId);

		existing.setBeerName(beer.getBeerName());
		existing.setPrice(beer.getPrice());
		existing.setUpc(beer.getUpc());
		existing.setQuantityOnHand(beer.getQuantityOnHand());

		beerMapa.put(existing.getId(), existing);
		return Optional.of(existing);

	}

	@Override
	public Boolean deleteById(UUID beerId) {
		// TODO Auto-generated method stub
		if (beerMapa.containsKey(beerId)) {
			beerMapa.remove(beerId);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
		BeerDTO existing = beerMapa.get(beerId);
		if (existing == null) {
			return Optional.empty();
		}
		if (StringUtils.hasText(beer.getBeerName())) {
			existing.setBeerName(beer.getBeerName());
		}

		if (beer.getBeerStyle() != null) {
			existing.setBeerStyle(beer.getBeerStyle());
		}

		if (beer.getPrice() != null) {
			existing.setPrice(beer.getPrice());
		}

		if (beer.getQuantityOnHand() != null) {
			existing.setQuantityOnHand(beer.getQuantityOnHand());
		}

		if (StringUtils.hasText(beer.getUpc())) {
			existing.setUpc(beer.getUpc());
		}

		return Optional.of(existing);

	}
}
