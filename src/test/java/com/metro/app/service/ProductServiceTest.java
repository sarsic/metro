package com.metro.app.service;

import com.metro.app.exception.ResourceNotFoundException;
import com.metro.app.repository.Product;
import com.metro.app.repository.ProductRepository;
import com.metro.app.service.model.PageResult;
import com.metro.app.service.model.request.product.ProductRequest;
import com.metro.app.service.model.view.product.ProductView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    @Mock private ProductRepository productRepository;
    private ProductService productService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        productService = new ProductService(productRepository);
    }

    @Test
    public void creatProduct() {
        final String name = "product";
        final Double price = 201.1;
        final ProductRequest productRequest = new ProductRequest(name, price);
        final Product expected = new Product(name, price);
        expected.setId(214L);
        when(productRepository.save(new Product(name, price))).thenReturn(expected);
        final ProductView actual = productService.creatProduct(productRequest);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }

    @Test
    public void updateProduct() {
        final String name = "product";
        final Double price = 201.1;
        final ProductRequest productRequest = new ProductRequest(name, price);
        final Product expected = new Product(name, price);
        final long id = 214L;
        expected.setId(id);
        when(productRepository.findById(id)).thenReturn(Optional.of(expected));
        when(productRepository.save(expected)).thenReturn(expected);
        final ProductView actual = productService.updateProduct(id, productRequest);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
    }
    @Test
    public void updateProductNotFound() {
        final String name = "product";
        final Double price = 201.1;
        final ProductRequest productRequest = new ProductRequest(name, price);
        final Product expected = new Product(name, price);
        final long id = 214L;
        expected.setId(id);
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        final ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{ productService.updateProduct(id,
                                                                                                                                     productRequest);});
        assertEquals("Product not found with id=" + id, exception.getMessage());
    }

    @Test
    public void getProducts() {
        final int page = 0;
        final int size = 2;
        final String name = "product";
        final Double price = 201.1;
        final Product expected = new Product(name, price);
        final long id = 214L;
        expected.setId(id);
        final List<Product> products = Collections.singletonList(expected);
        final Page<Product> productPage = new PageImpl<>(products, PageRequest.of(page, size), products.size());
        when(productRepository.findAll(PageRequest.of(page, size))).thenReturn(productPage);
        final PageResult<ProductView> actual = productService.getProducts(page, size);
        assertEquals(productPage.getTotalPages(), actual.getTotalPages());
        assertEquals(productPage.getContent().size(), actual.getItems().size());
        assertEquals(productPage.getContent().get(0).getId(), actual.getItems().get(0).getId());
        assertEquals(productPage.getContent().get(0).getName(), actual.getItems().get(0).getName());
        assertEquals(productPage.getContent().get(0).getPrice(), actual.getItems().get(0).getPrice());
    }
}
