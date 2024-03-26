/**
 * 
 */
package com.francisco5em.springrestmvc.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.francisco5em.springrestmvc.entities.Beer;
import com.francisco5em.springrestmvc.entities.BeerOrder;
import com.francisco5em.springrestmvc.entities.BeerOrderShipment;
import com.francisco5em.springrestmvc.entities.Customer;

/**
 * Creado por Francisco E.
 */
//@ActiveProfiles("test")
//@DataJpaTest
@SpringBootTest
class BeerOrderRepositoryTest {

	@Autowired
	BeerOrderRepository beerOrderRepo;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	BeerRepository beerRepository;

	Customer testCustomer;

	Beer testBeer;

	@BeforeEach
	void setUp() {
		testCustomer = customerRepository.findAll().get(0);
		testBeer = beerRepository.findAll().get(0);

	}

	@Transactional
	@Test
	void testBeerOrders() {
		BeerOrder beerOrder = BeerOrder.builder().customerRef("Test order")
				.customer(testCustomer)
				.beerOrderShipment(
						BeerOrderShipment.builder().trackingNumber("1235r").build())
				.build();

		BeerOrder savedBeerOrder = beerOrderRepo.save(beerOrder);

		System.out.println(savedBeerOrder.getCustomerRef());
	}

}
