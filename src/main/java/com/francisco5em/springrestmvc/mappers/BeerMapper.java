/**
 * 
 */
package com.francisco5em.springrestmvc.mappers;

import org.mapstruct.Mapper;

import com.francisco5em.springrestmvc.model.BeerDTO;
import com.francisco5em.springrestmvc.entities.Beer;

/**
 * Creado por Francisco E.
 */
@Mapper
public interface BeerMapper {

	Beer beerDTOtoBeer(BeerDTO dto);
	
	BeerDTO beerToBeerDTO(Beer beer);
}
