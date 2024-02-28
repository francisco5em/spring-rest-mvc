/**
 * 
 */
package com.francisco5em.springrestmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francisco5em.springrestmvc.model.BeerDTO;
import com.francisco5em.springrestmvc.services.BeerService;
import com.francisco5em.springrestmvc.services.BeerServiceImple;

/**
 * Creado por Francisco E.
 */
@WebMvcTest(BeerController.class)
class BeerControllerTest {

//	@Autowired
//	BeerController beerControl;

	@Autowired
	MockMvc mockmvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BeerService beerS;

	BeerServiceImple beerServiceImpl;

	@Captor
	ArgumentCaptor<UUID> uuidArgumentCaptor;

	@Captor
	ArgumentCaptor<BeerDTO> beerArgumentCaptor;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		beerServiceImpl = new BeerServiceImple();
	}

	@Test
	void testCreateNewBeer() throws Exception {
		BeerDTO beer = beerServiceImpl.listBeers().get(0);
		beer.setVersion(null);
		beer.setId(null);

		given(beerS.saveNewBeer(any(BeerDTO.class)))
				.willReturn(beerServiceImpl.listBeers().get(1));

		mockmvc.perform(post(BeerController.BEER_PATH).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beer)))
				.andExpect(status().isCreated()).andExpect(header().exists("Location"));
	}

	@Test
	void testUpdateBeer() throws Exception {
		BeerDTO beer = beerServiceImpl.listBeers().get(0);
		System.out.println("test id: " + beer.getId().toString());
		given(beerS.updateBeerById(any(), any())).willReturn(Optional.of(beer));

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
				.put(BeerController.BEER_PATH + "/" + beer.getId().toString())
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beer));

		/*
		 * mockmvc.perform(put(BeerController.BEER_PATH + "/" + beer.getId().toString())
		 * .accept(MediaType.APPLICATION_JSON) .contentType(MediaType.APPLICATION_JSON)
		 * .content(objectMapper.writeValueAsString(beer))) .andDo(print())
		 * .andExpect(status().isNoContent());
		 */
		mockmvc.perform(requestBuilder).andExpect(status().isNoContent());

		verify(beerS).updateBeerById(any(UUID.class), any(BeerDTO.class));

	}

	@Test
	void testDeleteBeer() throws Exception {
		BeerDTO beer = beerServiceImpl.listBeers().get(0);
		given(beerS.deleteById(any())).willReturn(true);

		mockmvc.perform(delete(BeerController.BEER_PATH + "/" + beer.getId())
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		verify(beerS).deleteById(uuidArgumentCaptor.capture());

		assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());

	}

	@Test
	void testPatchBeer() throws Exception {
		BeerDTO beer = beerServiceImpl.listBeers().get(0);
		given(beerS.patchBeerById(any(), any())).willReturn(Optional.of(beer));

		Map<String, Object> beerMap = new HashMap<>();
		beerMap.put("beerName", "New Name");

		mockmvc.perform(patch(BeerController.BEER_PATH + "/" + beer.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(beerMap)))
				.andExpect(status().isNoContent());

		verify(beerS).patchBeerById(uuidArgumentCaptor.capture(),
				beerArgumentCaptor.capture());

		assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
		assertThat(beerMap.get("beerName"))
				.isEqualTo(beerArgumentCaptor.getValue().getBeerName());

	}

	@Test
	void testListBeer() throws Exception {
		given(beerS.listBeers()).willReturn(beerServiceImpl.listBeers());

		mockmvc.perform(get(BeerController.BEER_PATH).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", is(3)));

	}

	@Test
	void getBeerbyId() throws Exception {
		BeerDTO testBeer = beerServiceImpl.listBeers().get(0);

		given(beerS.getBeerById(testBeer.getId())).willReturn(Optional.of(testBeer));

		mockmvc.perform(get(BeerController.BEER_PATH + "/" + testBeer.getId())
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
				.andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));

	}

}
