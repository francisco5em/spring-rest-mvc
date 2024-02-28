/**
 * 
 */
package com.francisco5em.springrestmvc.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.francisco5em.springrestmvc.model.BeerDTO;

/**
 * Creado por Francisco E.
 */
public interface BeerService {

	List<BeerDTO> listBeers();
	
	Optional<BeerDTO> getBeerById(UUID id);
	
	BeerDTO saveNewBeer(BeerDTO beer);

	Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);

	Boolean deleteById(UUID beerId);

	Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);
}
