/**
 * 
 */
package com.francisco5em.springrestmvc.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.francisco5em.springrestmvc.model.*;

import lombok.extern.slf4j.Slf4j;

/**
 * Creado por Francisco E.
 */
@Slf4j
@Service
public class BeerServiceImple implements BeerService {
	private Map<UUID, Beer> beerMapa;
	
	public BeerServiceImple() {
        this.beerMapa = new HashMap<>();

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sonora Especial")
                .beerStyle(BeerStyle.WHEAT)
                .upc("1151533")
                .price(new BigDecimal("40.00"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Bud-Heavy")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12322")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("White Tower")
                .beerStyle(BeerStyle.IPA)
                .upc("1252356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
        log.debug("constructor - in BeerServiceImple - 3 Beers created");

        beerMapa.put(beer1.getId(), beer1);
        beerMapa.put(beer2.getId(), beer2);
        beerMapa.put(beer3.getId(), beer3);
        
        log.debug("BeerServiceImple() - in BeerServiceImple - 3 Beers added to Hashmap");
        
    }

	@Override
    public List<Beer> listBeers(){
		
		log.debug("listBeers() - in BeerServiceImple.");
		
        return new ArrayList<>(beerMapa.values());
    }

    @Override
    public Beer getBeerById(UUID id) {

        log.debug("getBeerById(UUID id) - in BeerServiceImple. Id: " + id.toString());

        return beerMapa.get(id);
    }

}
