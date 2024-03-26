/**
 * 
 */
package com.francisco5em.springrestmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.TransactionSystemException;

import com.francisco5em.springrestmvc.Bootstrap.BootstrapData;
import com.francisco5em.springrestmvc.entities.Beer;
import com.francisco5em.springrestmvc.model.BeerStyle;
import com.francisco5em.springrestmvc.services.BeerCsvServiceImpl;

import jakarta.validation.ConstraintViolationException;

/**
 * Creado por Francisco E.
 */
@ActiveProfiles("test")
@DataJpaTest
//@SpringBootTest(properties = {"spring.config.name=application-test"})
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

	@Autowired
	BeerRepository beerRepo;
	
	@Test
	void testGetBeerListByName() {
		
		Page<Beer> list = beerRepo.findAllByBeerNameContainsIgnoreCase("IPA", null);
        assertThat(list.getContent().size()).isEqualTo(336);
		
	}

	@Test
	void testSaveBeerExceptionNameTooLong() {
		//{onstraintViolationException.class,
		assertThrows(ConstraintViolationException.class, () -> {

			Beer savedBeer = beerRepo.save(Beer.builder().beerName(
					"Mi Cerveza12345678901234567890123456789012345678901234567890Cerveza12345678901234567890123456789012345678901234567890")
					.beerStyle(BeerStyle.WHEAT).upc("82829234242")
					.price(new BigDecimal(12.5)).build());

			beerRepo.flush();

			assertThat(savedBeer).isNotNull();
			assertThat(savedBeer.getId()).isNotNull();

		});

	}

	@Test
	void testSaveBeer() {
		Beer savedBeer = beerRepo
				.save(Beer.builder().beerName("Mi Cerveza").beerStyle(BeerStyle.WHEAT)
						.upc("828294242").price(new BigDecimal(12.5)).build());

		beerRepo.flush();

		assertThat(savedBeer).isNotNull();
		assertThat(savedBeer.getId()).isNotNull();

	}

}
