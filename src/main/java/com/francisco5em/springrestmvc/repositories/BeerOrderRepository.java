/**
 * 
 */
package com.francisco5em.springrestmvc.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco5em.springrestmvc.entities.BeerOrder;

/**
 * Creado por Francisco E.
 */
public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID>{

}
