/**
 * 
 */
package com.francisco5em.springrestmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.francisco5em.springrestmvc.entities.Beer;
import com.francisco5em.springrestmvc.mappers.BeerMapper;
import com.francisco5em.springrestmvc.model.BeerDTO;
import com.francisco5em.springrestmvc.repositories.BeerRepository;

/**
 * Creado por Francisco E.
 */
@SpringBootTest
class BeerControllerIT {

	@Autowired
	BeerController beerControl;
	
	@Autowired
	BeerRepository beerRepo;
	
	@Autowired
	BeerMapper beerMap;

	/**
	 * Test method for
	 * {@link com.francisco5em.springrestmvc.controller.BeerController#listBeers()}.
	 */
	@Test
	void testListBeers() {
		List<BeerDTO> dtos = beerControl.listBeers();

		assertThat(dtos.size()).isEqualTo(3);

	}

	/**
	 * Test method for
	 * {@link com.francisco5em.springrestmvc.controller.BeerController#listBeers()}.
	 */
	@Rollback
	@Transactional
	@Test
	void testEmptyListBeers() {
		beerRepo.deleteAll();
		List<BeerDTO> dtos = beerControl.listBeers();

		assertThat(dtos.size()).isEqualTo(0);

	}

	/**
	 * Test method for
	 * {@link com.francisco5em.springrestmvc.controller.BeerController#getBeerById(java.util.UUID)}.
	 */
	@Test
	void testGetBeerById() {
		Beer beer = beerRepo.findAll().get(0);

		BeerDTO dto = beerControl.getBeerById(beer.getId());

		assertThat(dto).isNotNull();
	}

	@Test
	void testBeerIdNotFound() {
		assertThrows(NotFoundException.class, () -> {
			beerControl.getBeerById(UUID.randomUUID());

		});

	}

	/**
	 * Test method for
	 * {@link com.francisco5em.springrestmvc.controller.BeerController#handlePost(com.francisco5em.springrestmvc.model.BeerDTO)}.
	 */
	@Rollback
	@Transactional
	@Test
	void testSaveNewBeer() {
		BeerDTO dto = BeerDTO.builder().beerName("The BEEER").build();

		ResponseEntity resEntity = beerControl.handlePost(dto);

		assertThat(resEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
		assertThat(resEntity.getHeaders().getLocation()).isNotNull();
		String[] UUIDloc = resEntity.getHeaders().getLocation().getPath().split("/");
		UUID savedUUuid = UUID.fromString(UUIDloc[4]);

		Beer beer = beerRepo.findById(savedUUuid).get();
		assertThat(beer).isNotNull();

	}

	/**
	 * Test method for
	 * {@link com.francisco5em.springrestmvc.controller.BeerController#updateBeerPatchById(java.util.UUID, com.francisco5em.springrestmvc.model.BeerDTO)}.
	 */
	@Rollback
	@Transactional
	@Test
	void testUpdateBeerPatchById() {

		Beer beer = beerRepo.findAll().get(0);
		BeerDTO dto = beerMap.beerToBeerDTO(beer);
		dto.setId(null);
		dto.setVersion(null);
		dto.setBeerStyle(null);
		dto.setCreatedDate(null);
		dto.setPrice(new BigDecimal(47.5));
		dto.setQuantityOnHand(null);
		dto.setUpc(null);
		final String beerName = "La nueva";
		dto.setBeerName(beerName);

		ResponseEntity entity = beerControl.updateBeerPatchById(beer.getId(), dto);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

		Beer updatedBeer = beerRepo.findById(beer.getId()).get();
		assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
		assertThat(updatedBeer.getPrice()).isEqualTo(new BigDecimal(47.5));
		assertThat(updatedBeer.getUpc()).isEqualTo(beer.getUpc());
		assertThat(updatedBeer.getQuantityOnHand()).isEqualTo(beer.getQuantityOnHand());

	}

	/**
	 * Test method for exception handling on
	 * {@link com.francisco5em.springrestmvc.controller.BeerController#updateBeerPatchById(java.util.UUID, com.francisco5em.springrestmvc.model.BeerDTO)}.
	 */
	@Rollback
	@Transactional
	@Test
	void testUpdateBeerPatchByIdNotFound() {
		assertThrows(NotFoundException.class, () -> {
			beerControl.updateBeerPatchById(UUID.randomUUID(), BeerDTO.builder().build());
		});
	}

	/**
	 * Test method for
	 * {@link com.francisco5em.springrestmvc.controller.BeerController#deleteById(java.util.UUID)}.
	 */
	@Rollback
	@Transactional
	@Test
	void testDeleteById() {
		Beer beer = beerRepo.findAll().get(0);
		ResponseEntity entity = beerControl.deleteById(beer.getId());

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
		assertThat(beerRepo.findById(beer.getId())).isEmpty();

	}

	/**
	 * Test method for exception handling on
	 * {@link com.francisco5em.springrestmvc.controller.BeerController#deleteById(java.util.UUID)}.
	 */
	@Rollback
	@Transactional
	@Test
	void testDeleteByIdNotFound() {
		assertThrows(NotFoundException.class, () -> {
			beerControl.deleteById(UUID.randomUUID());
		});
	}

	/**
	 * Test method for
	 * {@link com.francisco5em.springrestmvc.controller.BeerController#updateById(java.util.UUID, com.francisco5em.springrestmvc.model.BeerDTO)}.
	 */
	@Rollback
	@Transactional
	@Test
	void testUpdateById() {
		Beer beer = beerRepo.findAll().get(0);
		BeerDTO dto = beerMap.beerToBeerDTO(beer);
		dto.setId(null);
		dto.setVersion(null);
		final String beerName = "La nueva";
		dto.setBeerName(beerName);

		ResponseEntity entity = beerControl.updateById(beer.getId(), dto);
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

		Beer updatedBeer = beerRepo.findById(beer.getId()).get();
		assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
	}

	/**
	 * Test method for exception handling on
	 * {@link com.francisco5em.springrestmvc.controller.BeerController#updateById(java.util.UUID, com.francisco5em.springrestmvc.model.BeerDTO)}.
	 */
	@Rollback
	@Transactional
	@Test
	void testUpdateByIdNotFound() {
		assertThrows(NotFoundException.class, () -> {
			beerControl.updateById(UUID.randomUUID(), BeerDTO.builder().build());
		});
	}

}
