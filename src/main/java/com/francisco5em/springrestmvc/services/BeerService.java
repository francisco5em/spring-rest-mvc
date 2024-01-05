/**
 * 
 */
package com.francisco5em.springrestmvc.services;

import java.util.List;
import java.util.UUID;

import com.francisco5em.springrestmvc.model.Beer;

/**
 * Creado por Francisco E.
 */
public interface BeerService {

	List<Beer> listBeers();
	
	Beer getBeerById(UUID id);
}
