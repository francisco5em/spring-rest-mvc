/**
 * 
 */
package com.francisco5em.springrestmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.francisco5em.springrestmvc.model.Beer;
import com.francisco5em.springrestmvc.services.BeerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Creado por Francisco E.
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
	
	private final BeerService beerS;
	
	//@RequestMapping(method = RequestMethod.GET)
	@GetMapping
    public List<Beer> listBeers(){
		log.debug("listBeers() - in BeerController - Obtaining List of Beers from Service");
		
		log.debug("listBeers() - in BeerController - Returning List of Beers");
        return beerS.listBeers();
    }
	 
	//@RequestMapping(value = "{beerId}", method = RequestMethod.GET)
	@GetMapping("{beerId}")
	public Beer getBeerById(@PathVariable("beerId") UUID beerId){

        log.debug("getBeerById(UUID id) - in BeerController - ID: "+beerId.toString());
        return beerS.getBeerById(beerId);
    }
	
	
}
