/**
 * 
 */
package com.francisco5em.springrestmvc.mappers;

import org.mapstruct.Mapper;

import com.francisco5em.springrestmvc.entities.Customer;
import com.francisco5em.springrestmvc.model.CustomerDTO;

/**
 * Creado por Francisco E.
 */
@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);

}
