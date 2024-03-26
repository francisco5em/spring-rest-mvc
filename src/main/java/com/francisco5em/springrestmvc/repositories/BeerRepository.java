/**
 * 
 */
package com.francisco5em.springrestmvc.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco5em.springrestmvc.entities.Beer;
import com.francisco5em.springrestmvc.model.BeerStyle;

/**
 * Creado por Francisco E.
 */

public interface BeerRepository extends JpaRepository<Beer, UUID> {
	
	//findAllByBeerNameIsLikeIgnoreCase
	
	Page<Beer> findAllByBeerNameContainsIgnoreCase(String beerName, Pageable pageable);
	
	Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageable);
	
	Page<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(String beerName, BeerStyle beerStyle, Pageable pageable);
	

} 
