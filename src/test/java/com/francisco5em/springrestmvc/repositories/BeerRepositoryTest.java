/**
 * 
 */
package com.francisco5em.springrestmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.francisco5em.springrestmvc.entities.Beer;

/**
 * Creado por Francisco E.
 */
@DataJpaTest
class BeerRepositoryTest {
	
	@Autowired
	BeerRepository beerRepo;

	@Test
	void test() {
		//fail("Not yet implemented");
	}
	
	@Test
	void testSaveBeer() {
		Beer savedBeer=beerRepo.save(Beer.builder()
				.beerName("Mi Cerveza")
				.build());
		assertThat(savedBeer).isNotNull();
		assertThat(savedBeer.getId()).isNotNull();
		
		
		
	}
	

}
