/**
 * 
 */
package com.francisco5em.springrestmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.francisco5em.springrestmvc.entities.Customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;

/**
 * Creado por Francisco E.
 */
@ActiveProfiles("test")
@DataJpaTest
class CustomerRepositoryTest {
	
	@Autowired
    CustomerRepository customerRepository;

	@Transactional
	@Rollback
	@Test
	void testSaveCustomer() {
		Customer customer = customerRepository
				.save(Customer.builder().name("Nuevo Nombre").build());

		assertThat(customer.getId()).isNotNull();
	}

}
