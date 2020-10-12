package com.metro.app.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metro.app.error.ResourceNotFoundException;
import com.metro.app.error.RestExceptionHandler;
import com.metro.app.service.ProductService;
import com.metro.app.service.model.resource.product.ProductResource;
import com.metro.app.service.model.view.product.ProductView;
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
        final ProductResource productResource = new ProductResource(name, price);
        when(productService.updateProduct(id, productResource)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.put(ProductController.PRODUCT_FOR_ID, id)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsBytes(productResource))
                                              .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateProduct() throws Exception {
        final Long id = 1L;
        final String name = "product";
        final Double price = 201.1;
        final ProductResource productResource = new ProductResource(name, price);
        final ProductView expected = new ProductView(id, name, price);
        when(productService.updateProduct(id, productResource)).thenReturn(expected);
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(ProductController.PRODUCT_FOR_ID, id)
                                                                       .contentType(MediaType.APPLICATION_JSON)
                                                                       .content(objectMapper.writeValueAsBytes(productResource))
                                                                       .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(
                status().isOk()).andReturn();
        final ProductView actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ProductView.class);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    public void testCreateProduct() throws Exception {
        final String name = "product";
        final Double price = 201.1;
        final ProductResource productResource = new ProductResource(name, price);
        final Long id = 1L;
        final ProductView expected = new ProductView(id, name, price);
        when(productService.creatProduct(productResource)).thenReturn(expected);
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(ProductController.PRODUCT)
                                                                       .contentType(MediaType.APPLICATION_JSON)
                                                                       .content(objectMapper.writeValueAsBytes(productResource))
                                                                       .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(
                status().isOk()).andReturn();
        final ProductView actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(), ProductView.class);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }
}
