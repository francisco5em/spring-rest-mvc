/**
 * 
 */
package com.francisco5em.springrestmvc.repositories;

import com.francisco5em.springrestmvc.entities.Customer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Creado por Francisco E.
 */
public interface  CustomerRepository extends JpaRepository<Customer, UUID>  {

}
