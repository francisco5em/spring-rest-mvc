/**
 * 
 */
package com.francisco5em.springrestmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.greaterThan;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.GreaterThan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.francisco5em.springrestmvc.Bootstrap.BootstrapData;
import com.francisco5em.springrestmvc.config.SpringSecConfig;
import com.francisco5em.springrestmvc.entities.Beer;
import com.francisco5em.springrestmvc.mappers.BeerMapper;
import com.francisco5em.springrestmvc.model.BeerDTO;
import com.francisco5em.springrestmvc.model.BeerStyle;
import com.francisco5em.springrestmvc.repositories.BeerRepository;
import com.francisco5em.springrestmvc.services.BeerCsvServiceImpl;
import com.francisco5em.springrestmvc.services.BeerService;
import com.francisco5em.springrestmvc.services.BeerServiceImple;

/**
 * Creado por Francisco E.
 */
@SpringBootTest
//@Import(SpringSecConfig.class)
//@ContextConfiguration(classes = SpringSecConfig.class)
class BeerControllerIT {

    @Autowired
    BeerController beerControl;

    @Autowired
    BeerRepository beerRepo;

    @Autowired
    BeerMapper beerMap;

    @Autowired
    ObjectMapper objMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                // .alwaysDo(print())
                .apply(springSecurity()).build();
    }

    // @Rollback
    // @Transactional
    @Test
    void testPatchBeerBadName() throws Exception {
        Beer beer = beerRepo.findAll().get(0);

        // given(beerS.patchBeerById(any(), any())).willReturn(Optional.of(beer));

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName",
                "New Name12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");

        MvcResult res = mockMvc.perform(patch(BeerController.BEER_PATH + "/" + beer.getId())
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(beerMap))).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()", is(1))).andReturn();
    }

    @Test
    void tesListBeersByStyleAndNameShowInventoryTruePage2() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH).queryParam("beerName", "IPA")
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .queryParam("beerStyle", BeerStyle.IPA.name()).queryParam("showInventory", "true")
                .queryParam("pageNumber", "2").queryParam("pageSize", "50"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content.size()", is(50)))
                .andExpect(jsonPath("$.content[0].quantityOnHand").value(IsNull.notNullValue()));
    }

    @Test
    void tesListBeersByStyleAndNameShowInventoryTrue() throws Exception {
        // MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put
        MvcResult rers = mockMvc.perform(get(BeerController.BEER_PATH).queryParam("beerName", "IPA")
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .queryParam("beerStyle", BeerStyle.IPA.name()).queryParam("showInventory", "true")
                .queryParam("pageSize", "800")).andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(310)))
                .andExpect(jsonPath("$.content[0].quantityOnHand").value(IsNull.notNullValue()))
                .andReturn();

        System.out.println("TESTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println(rers.getResponse().getContentAsString());
    }

    @Test
    void tesListBeersByStyleAndNameShowInventoryFalse() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH).queryParam("beerName", "IPA")
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .queryParam("beerStyle", BeerStyle.IPA.name()).queryParam("showInventory", "false")
                .queryParam("pageSize", "800")).andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(310)))
                .andExpect(jsonPath("$.content[0].quantityOnHand").value(IsNull.nullValue()));
    }

    @Test
    void tesListBeersByStyleAndName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor).queryParam("beerName", "IPA")
                .queryParam("beerStyle", BeerStyle.IPA.name()).queryParam("pageSize", "800"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content.size()", is(310)));
    }

    @Test
    void tesListBeersByStyle() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .queryParam("beerStyle", BeerStyle.IPA.name()).queryParam("pageSize", "800"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content.size()", greaterThan(500)));
    }

    @Test
    void tesListBeersByName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor).queryParam("beerName", "IPA")
                .queryParam("pageSize", "800")).andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(336)));
    }

    /**
     * Test method for
     * {@link com.francisco5em.springrestmvc.controller.BeerController#listBeers()}.
     * 
     * @throws Exception
     */
    @Test
    void testListBeers() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor).queryParam("pageSize", "2400"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content.size()", is(1000)));
        /*
         * Old code that should probably be deleted. Page<BeerDTO> dtos =
         * beerControl.listBeers(null, null, false, 1, 2400);
         * 
         * assertThat(dtos.getContent().size()).isEqualTo(1000);
         */

    }

    /**
     * Test method for
     * {@link com.francisco5em.springrestmvc.controller.BeerController#listBeers()}.
     * 
     * @throws Exception
     */
    @Rollback
    @Transactional
    @Test
    void testEmptyListBeers() throws Exception {
        beerRepo.deleteAll();
        mockMvc.perform(get(BeerController.BEER_PATH)
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor).queryParam("pageSize", "2400"))
                .andExpect(status().isOk()).andExpect(jsonPath("$.content.size()", is(0)));

//          Page<BeerDTO> dtos = beerControl.listBeers(null, null, false, 1, 25);
//          assertThat(dtos.getContent().size()).isEqualTo(0);

    }

    /**
     * Test method for
     * {@link com.francisco5em.springrestmvc.controller.BeerController#getBeerById(java.util.UUID)}.
     * 
     * @throws Exception
     * @throws JsonProcessingException
     */
    @Test
    void testGetBeerById() throws JsonProcessingException, Exception {
        Beer beer = beerRepo.findAll().get(0);

        ResultActions result = mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(beerMap))).andExpect(status().isNoContent());
        // System.out.println(result.andReturn().getResponse().getContentAsString());

        assertThat(result.andReturn().getResponse().getContentAsString()
                .contains(beer.getId().toString()));
        // BeerDTO dto = beerControl.getBeerById(beer.getId());

        // assertThat(dto).isNotNull();
    }

    /**
     * Test method for:
     * {@link com.francisco5em.springrestmvc.controller.BeerController#getBeerById(java.util.UUID)}.
     * When the Beer doesn't exist in the database should return 404
     * 
     * @throws Exception
     * @throws JsonProcessingException
     */
    @Test
    void testBeerIdNotFound() throws JsonProcessingException, Exception {
        mockMvc.perform(patch(BeerController.BEER_PATH_ID, UUID.randomUUID())
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(beerMap))).andExpect(status().isNotFound());
        /*
         * assertThrows(NotFoundException.class, () -> {
         * beerControl.getBeerById(UUID.randomUUID());
         * 
         * });
         */

    }

    /**
     * Test method for
     * {@link com.francisco5em.springrestmvc.controller.BeerController#handlePost(com.francisco5em.springrestmvc.model.BeerDTO)}.
     * 
     * @throws Exception
     * @throws JsonProcessingException
     */
    @SuppressWarnings("rawtypes")
    @Rollback
    @Transactional
    @Test
    void testSaveNewBeer() throws JsonProcessingException, Exception {
        BeerDTO dto = BeerDTO.builder().beerName("The BEEEEEEEEEER").beerStyle(BeerStyle.PORTER)
                .upc("1685416851353").price(new BigDecimal(15.3)).quantityOnHand(633).build();
        // Beer beer = beerRepo.findAll().get(0);

        ResultActions resEntity = mockMvc.perform(post(BeerController.BEER_PATH)
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(dto))).andExpect(status().is(201));

        // beerControl.handlePost(dto);

        assertThat(resEntity.andReturn().getResponse().getHeader("Location")).isNotNull();
        String[] UUIDloc = resEntity.andReturn().getResponse().getHeader("Location").split("/");
        UUID savedUUuid = UUID.fromString(UUIDloc[4]);

        Beer beer = beerRepo.findById(savedUUuid).get();
        assertThat(beer).isNotNull();

    }

    /**
     * Test method for
     * {@link com.francisco5em.springrestmvc.controller.BeerController#updateBeerPatchById(java.util.UUID, com.francisco5em.springrestmvc.model.BeerDTO)}.
     * 
     * @throws Exception
     * @throws JsonProcessingException
     */
    @Rollback
    @Transactional
    @Test
    void testUpdateBeerPatchById() throws JsonProcessingException, Exception {

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

        ResultActions res = mockMvc.perform(patch(BeerController.BEER_PATH + "/" + beer.getId())
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(dto))).andExpect(status().is(204));

        Beer updatedBeer = beerRepo.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
        assertThat(updatedBeer.getPrice()).isEqualTo(new BigDecimal(47.5));
        assertThat(updatedBeer.getUpc()).isEqualTo(beer.getUpc());
        assertThat(updatedBeer.getQuantityOnHand()).isEqualTo(beer.getQuantityOnHand());

    }

    /**
     * Test method for exception handling on
     * {@link com.francisco5em.springrestmvc.controller.BeerController#updateBeerPatchById(java.util.UUID, com.francisco5em.springrestmvc.model.BeerDTO)}.
     * 
     * @throws Exception
     * @throws JsonProcessingException
     */
    @Rollback
    @Transactional
    @Test
    void testUpdateBeerPatchByIdNotFound() throws JsonProcessingException, Exception {
        mockMvc.perform(patch(BeerController.BEER_PATH + "/" + UUID.randomUUID())
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(BeerDTO.builder().build())))
                .andExpect(status().isNotFound());

        /*
         * assertThrows(NotFoundException.class, () -> {
         * beerControl.updateBeerPatchById(UUID.randomUUID(),
         * BeerDTO.builder().build()); });
         */
    }

    /**
     * Test method for
     * {@link com.francisco5em.springrestmvc.controller.BeerController#deleteById(java.util.UUID)}.
     * 
     * @throws Exception
     */
    @Rollback
    @Transactional
    @Test
    void testDeleteById() throws Exception {

        Beer beer = beerRepo.findAll().get(0);
        // ResponseEntity entity = beerControl.deleteById(beer.getId());

        ResultActions res = mockMvc.perform(delete(BeerController.BEER_PATH + "/" + beer.getId())
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // assertThat(entity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepo.findById(beer.getId())).isEmpty();

    }

    /**
     * Test method for exception handling on
     * {@link com.francisco5em.springrestmvc.controller.BeerController#deleteById(java.util.UUID)}.
     * 
     * @throws Exception
     */
    @Rollback
    @Transactional
    @Test
    void testDeleteByIdNotFound() throws Exception {

        mockMvc.perform(delete(BeerController.BEER_PATH + "/" + UUID.randomUUID())
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)).andExpect(status().isNotFound());
        /*
         * assertThrows(NotFoundException.class, () -> {
         * beerControl.deleteById(UUID.randomUUID()); });
         */
    }

    /**
     * Test method for
     * {@link com.francisco5em.springrestmvc.controller.BeerController#updateById(java.util.UUID, com.francisco5em.springrestmvc.model.BeerDTO)}.
     * 
     * @throws Exception
     * @throws JsonProcessingException
     */
    @Rollback
    @Transactional
    @Test
    void testUpdateById() throws JsonProcessingException, Exception {
        Beer beer = beerRepo.findAll().get(0);
        BeerDTO dto = beerMap.beerToBeerDTO(beer);
        // dto.setId(null);
        dto.setVersion(null);
        final String beerName = "La nueva";
        dto.setBeerName(beerName);

        // ResponseEntity entity = beerControl.updateById(beer.getId(), dto);

        mockMvc.perform(put(BeerController.BEER_PATH + "/" + beer.getId())
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(dto))).andExpect(status().isNoContent());

        // assertThat(entity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepo.findById(beer.getId()).get();
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

    /**
     * Test method for exception handling on
     * {@link com.francisco5em.springrestmvc.controller.BeerController#updateById(java.util.UUID, com.francisco5em.springrestmvc.model.BeerDTO)}.
     * 
     * @throws Exception
     * @throws JsonProcessingException
     */
    @Rollback
    @Transactional
    @Test
    void testUpdateByIdNotFound() throws JsonProcessingException, Exception {
        mockMvc.perform(put(BeerController.BEER_PATH + "/" + UUID.randomUUID())
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS)) //original
                // config for test with httpbasic
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(BeerDTO.builder().build())))
                .andExpect(status().isNotFound());

//        assertThrows(NotFoundException.class, () -> {
//            beerControl.updateById(UUID.randomUUID(), BeerDTO.builder().build());
//        });
    }

    /**
     * Testing functionality of Spring Security.
     */
    @Rollback
    @Transactional
    @Test
    void testSecurityRequiriments() throws JsonProcessingException, Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                // .with(httpBasic(BeerControllerTest.USER, BeerControllerTest.PASS))
                .queryParam("pageSize", "2400")).andExpect(status().isUnauthorized());

    }

}
