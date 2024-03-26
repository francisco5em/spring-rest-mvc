/**
 * 
 */
package com.francisco5em.springrestmvc.controller;

import com.francisco5em.springrestmvc.entities.Customer;
import com.francisco5em.springrestmvc.mappers.CustomerMapper;
import com.francisco5em.springrestmvc.model.CustomerDTO;
import com.francisco5em.springrestmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Creado por Francisco E.
 */
@SpringBootTest
class CustomerControllerIT {

	 @Autowired
	    CustomerRepository customerRepo;

	    @Autowired
	    CustomerController customerController;

	    @Autowired
	    CustomerMapper customerMap;

	    @Rollback
	    @Transactional
	    @Test
	    void deleteByIdFound() {
	        Customer customer = customerRepo.findAll().get(0);

	        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());
	        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

	        assertThat(customerRepo.findById(customer.getId()).isEmpty());
	    }

	    @Test
	    void testDeleteNotFound() {
	        assertThrows(NotFoundException.class, () -> {
	            customerController.deleteCustomerById(UUID.randomUUID());
	        });
	    }

	    @Test
	    void testUpdateNotFound() {
	        assertThrows(NotFoundException.class, () -> {
	            customerController.updateCustomerByID(UUID.randomUUID(), CustomerDTO.builder().build());
	        });
	    }

	    @Rollback
	    @Transactional
	    @Test
	    void updateExistingBeer() {
	        Customer customer = customerRepo.findAll().get(0);
	        CustomerDTO customerDTO = customerMap.customerToCustomerDto(customer);
	        customerDTO.setId(null);
	        customerDTO.setVersion(null);
	        final String customerName = "NUEVA";
	        customerDTO.setName(customerName);

	        ResponseEntity responseEntity = customerController.updateCustomerByID(customer.getId(), customerDTO);
	        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

	        Customer updatedCustomer = customerRepo.findById(customer.getId()).get();
	        assertThat(updatedCustomer.getName()).isEqualTo(customerName);
	    }

	    @Rollback
	    @Transactional
	    @Test
	    void saveNewBeerTest() {
	       CustomerDTO customerDTO = CustomerDTO.builder()
	               .name("TEST")
	               .build();

	        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

	        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
	        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

	        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
	        UUID savedUUID = UUID.fromString(locationUUID[4]);

	        Customer customer = customerRepo.findById(savedUUID).get();
	        assertThat(customer).isNotNull();
	    }

	    @Rollback
	    @Transactional
	    @Test
	    void testListAllEmptyList() {
	        customerRepo.deleteAll();
	        List<CustomerDTO> dtos = customerController.listAllCustomers();

	        assertThat(dtos.size()).isEqualTo(0);
	    }

	    @Test
	    void testListAll() {
	        List<CustomerDTO> dtos = customerController.listAllCustomers();

	        assertThat(dtos.size()).isEqualTo(3);
	    }

	    @Test
	    void testGetByIdNotFound() {
	        assertThrows(NotFoundException.class, () -> {
	            customerController.getCustomerById(UUID.randomUUID());
	        });
	    }

	    @Test
	    void testGetById() {
	        Customer customer = customerRepo.findAll().get(0);
	        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
	        assertThat(customerDTO).isNotNull();
	    }

}
