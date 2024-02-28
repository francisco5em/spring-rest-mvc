/**
 * 
 */
package com.francisco5em.springrestmvc.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.francisco5em.springrestmvc.model.BeerDTO;
import com.francisco5em.springrestmvc.services.BeerService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Creado por Francisco E.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
//@RequestMapping(BeerController.BEER_PATH)
public class BeerController {

	private final BeerService beerS;

	public static final String BEER_PATH = "/api/v1/beer";
	public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

	@PatchMapping(BEER_PATH_ID)
	public ResponseEntity updateBeerPatchById(@PathVariable("beerId") UUID beerId,
			@RequestBody BeerDTO beer) {

		if(beerS.patchBeerById(beerId, beer).isEmpty()) {
			throw new NotFoundException();
		}

		return new ResponseEntity(HttpStatus.NO_CONTENT);

	}

	@DeleteMapping(BEER_PATH_ID)
	public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId) {
		if(!beerS.deleteById(beerId)) {
			throw new NotFoundException();
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT);

	}

	@PutMapping(value = BEER_PATH_ID)
	public ResponseEntity updateById(@PathVariable("beerId") UUID beerId,
			@RequestBody BeerDTO beer) {

		log.debug("Requested beer id:" + beerId.toString());
		/*
		 * Boolean b=beerS.updateBeerById(beerId, beer); if (b) { return new
		 * ResponseEntity(HttpStatus.NO_CONTENT); } else { return new
		 * ResponseEntity(HttpStatus.NOT_FOUND);
		 * 
		 * }
		 */

		if(beerS.updateBeerById(beerId, beer).isEmpty()) {
			throw new NotFoundException();
		}

		return new ResponseEntity(HttpStatus.NO_CONTENT);

	}

	@PostMapping(BEER_PATH)
	// @RequestMapping(method = RequestMethod.POST)
	public ResponseEntity handlePost(@RequestBody BeerDTO beer) {

		BeerDTO savedBeer = beerS.saveNewBeer(beer);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	// @RequestMapping(method = RequestMethod.GET)
	@GetMapping(BEER_PATH)
	public List<BeerDTO> listBeers() {
		log.debug(
				"listBeers() - in BeerController - Obtaining List of Beers from Service");

		log.debug("listBeers() - in BeerController - Returning List of Beers");
		return beerS.listBeers();
	}

	// @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
	@GetMapping(BEER_PATH_ID)
	public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId) {

		log.debug("getBeerById(UUID id) - in BeerController - ID: " + beerId.toString());
		return beerS.getBeerById(beerId).orElseThrow(NotFoundException::new);
	}

}
