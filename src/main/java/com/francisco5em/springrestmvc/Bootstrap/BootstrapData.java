/**
 * 
 */
package com.francisco5em.springrestmvc.Bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.francisco5em.springrestmvc.entities.Beer;
import com.francisco5em.springrestmvc.model.BeerDTO;
import com.francisco5em.springrestmvc.model.BeerStyle;
import com.francisco5em.springrestmvc.repositories.BeerRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

/**
 * Creado por Francisco E.
 */
@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
	private final BeerRepository beerRepository;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		loadBeerDefaultData();

	}

	private void loadBeerDefaultData() {
		if (beerRepository.count() == 0) {
			Beer beer1 = Beer.builder().id(UUID.randomUUID()).version(1)
					.beerName("Sonora Especial").beerStyle(BeerStyle.WHEAT).upc("1151533")
					.price(new BigDecimal("40.00")).quantityOnHand(122)
					.createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now())
					.build();

			Beer beer2 = Beer.builder().id(UUID.randomUUID()).version(1)
					.beerName("Bud-Heavy").beerStyle(BeerStyle.PALE_ALE).upc("12322")
					.price(new BigDecimal("11.99")).quantityOnHand(392)
					.createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now())
					.build();

			Beer beer3 = Beer.builder().id(UUID.randomUUID()).version(1)
					.beerName("White Tower").beerStyle(BeerStyle.IPA).upc("1252356")
					.price(new BigDecimal("13.99")).quantityOnHand(144)
					.createdDate(LocalDateTime.now()).updateDate(LocalDateTime.now())
					.build();

			beerRepository.save(beer1);
			beerRepository.save(beer2);
			beerRepository.save(beer3);

		}

	}

}
