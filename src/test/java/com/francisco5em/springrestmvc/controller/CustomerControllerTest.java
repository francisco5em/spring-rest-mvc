/**
 * 
 */
package com.francisco5em.springrestmvc.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.francisco5em.springrestmvc.config.SpringSecConfig;
import com.francisco5em.springrestmvc.model.CustomerDTO;
import com.francisco5em.springrestmvc.services.CustomerService;
import com.francisco5em.springrestmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Creado por Francisco E.
 */
@WebMvcTest(CustomerController.class)
@Import(SpringSecConfig.class)
class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objMapper;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<CustomerDTO> customerArgCaptor;

    @Test
    void testPatchCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("name", "Sonora ESPECIAL");

        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(),
                customerArgCaptor.capture());

        assertThat(uuidArgumentCaptor.getValue()).isEqualTo(customer.getId());
        assertThat(customerArgCaptor.getValue().getName()).isEqualTo(customerMap.get("name"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);

        given(customerService.deleteCustomerById(any())).willReturn(true);

        mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());

        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);

        given(customerService.updateCustomerById(any(), any()))
                .willReturn(Optional.of(CustomerDTO.builder().build()));

        mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .content(objMapper.writeValueAsString(customer))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomerById(uuidArgumentCaptor.capture(),
                any(CustomerDTO.class));

        assertThat(customer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
    }

    @Test
    void testCreateCustomer() throws Exception {
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);
        customer.setId(null);
        customer.setVersion(null);

        given(customerService.saveNewCustomer(any(CustomerDTO.class)))
                .willReturn(customerServiceImpl.getAllCustomers().get(1));

        mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(objMapper.writeValueAsString(customer))).andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void listAllCustomers() throws Exception {
        given(customerService.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH)
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerByIdNotFound() throws Exception {

        given(customerService.getCustomerById(any(UUID.class))).willReturn(Optional.empty());

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, UUID.randomUUID())
                .with(BeerControllerTest.jwtRequestPostProcessor))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustomerById() throws Exception {
        CustomerDTO customer = customerServiceImpl.getAllCustomers().get(0);

        given(customerService.getCustomerById(customer.getId())).willReturn(Optional.of(customer));

        mockMvc.perform(get(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                .with(BeerControllerTest.jwtRequestPostProcessor)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(customer.getName())));
    }

}
