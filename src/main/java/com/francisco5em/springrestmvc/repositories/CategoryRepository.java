/**
 * 
 */
package com.francisco5em.springrestmvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco5em.springrestmvc.entities.Category;

import java.util.UUID;

/**
 * Creado por Francisco E.
 */
public interface CategoryRepository extends JpaRepository<Category, UUID> {

}
