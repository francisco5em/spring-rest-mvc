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
import com.francisco5em.springrestmvc.entities.Category;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Creado por Francisco E.
 */
@SpringBootTest
class CategoryRepositoryTest {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	BeerRepository beerRepository;
	Beer testBeer;

	@BeforeEach
	void setUp() {
		testBeer = beerRepository.findAll().get(0);
	}

	@Transactional
	@Test
	void testAddCategory() {
		Category savedCat = categoryRepository
				.save(Category.builder().description("Ales").build());

		testBeer.addCategory(savedCat);
		Beer saveBeer = beerRepository.save(testBeer);

		System.out.println(saveBeer.getBeerName());

	}
}
