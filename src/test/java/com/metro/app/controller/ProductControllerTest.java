package com.metro.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metro.app.exception.ResourceNotFoundException;
import com.metro.app.exception.RestExceptionHandler;
import com.metro.app.service.ProductService;
import com.metro.app.service.request.product.ProductRequest;
import com.metro.app.service.response.product.ProductResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest {
    @Mock ProductService productService;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService)).setControllerAdvice(
                new RestExceptionHandler()).setMessageConverters(converter).build();
    }

    @Test
    public void testProductNotFound() throws Exception {
        final Long id = 1L;
        final String name = "product";
        final Double price = 201.1;
        final ProductRequest productRequest = new ProductRequest(name, price);
        when(productService.updateProduct(id, productRequest)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put(ProductController.PRODUCTS_ENDPOINT + "/" + ProductController.PRODUCTS_ID, id)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsBytes(productRequest))
                                              .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        final Long id = 1L;
        final String name = "product";
        final Double price = 201.1;
        final ProductRequest productRequest = new ProductRequest(name, price);
        final ProductResponse expected = new ProductResponse(id, name, price);
        when(productService.updateProduct(id, productRequest)).thenReturn(expected);
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ProductController.PRODUCTS_ENDPOINT + "/" + ProductController.PRODUCTS_ID, id)
                                                                       .contentType(MediaType.APPLICATION_JSON)
                                                                       .content(objectMapper.writeValueAsBytes(productRequest))
                                                                       .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(
                status().isOk()).andReturn();
        final ProductResponse actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                                                              ProductResponse.class);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    public void testCreateProduct() throws Exception {
        final String name = "product";
        final Double price = 201.1;
        final ProductRequest productRequest = new ProductRequest(name, price);
        final Long id = 1L;
        final ProductResponse expected = new ProductResponse(id, name, price);
        when(productService.creatProduct(productRequest)).thenReturn(expected);
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ProductController.PRODUCTS_ENDPOINT)
                                                                       .contentType(MediaType.APPLICATION_JSON)
                                                                       .content(objectMapper.writeValueAsBytes(productRequest))
                                                                       .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(
                status().isOk()).andReturn();
        final ProductResponse actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                                                              ProductResponse.class);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }
}
