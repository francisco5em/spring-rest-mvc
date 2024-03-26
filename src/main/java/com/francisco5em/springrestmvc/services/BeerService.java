/**
 * 
 */
package com.francisco5em.springrestmvc.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.francisco5em.springrestmvc.model.BeerDTO;
import com.francisco5em.springrestmvc.model.BeerStyle;

/**
 * Creado por Francisco E.
 */
public interface BeerService {

	Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);
	
	Optional<BeerDTO> getBeerById(UUID id);
	
	BeerDTO saveNewBeer(BeerDTO beer);

	Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

	Boolean deleteById(UUID beerId);

	Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);
}
