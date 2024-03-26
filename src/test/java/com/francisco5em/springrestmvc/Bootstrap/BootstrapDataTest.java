/**
 * 
 */
package com.francisco5em.springrestmvc.Bootstrap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.francisco5em.springrestmvc.Bootstrap.BootstrapData;
import com.francisco5em.springrestmvc.repositories.BeerRepository;
import com.francisco5em.springrestmvc.repositories.CustomerRepository;
import com.francisco5em.springrestmvc.services.BeerCsvService;
import com.francisco5em.springrestmvc.services.BeerCsvServiceImpl;



/**
 * Creado por Francisco E.
 */
@ActiveProfiles("test")
@Import(BeerCsvServiceImpl.class)
@DataJpaTest
class BootstrapDataTest {
	
	@Autowired
    BeerRepository beerRepo;
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
    BeerCsvService csvService;


    BootstrapData bootstrapData;


	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		bootstrapData = new BootstrapData(beerRepo,customerRepo,csvService);
		
	}

	@Rollback
	@Transactional
	@Test
	void testRun() throws Exception {
        bootstrapData.run(null);
        

        assertThat(beerRepo.count()).isGreaterThan(1000);
        assertThat(customerRepo.count()).isEqualTo(3);
	}

}
