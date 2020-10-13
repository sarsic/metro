package com.metro.app.service;

import com.metro.app.exception.ResourceNotFoundException;
import com.metro.app.repository.ProductRepository;
import com.metro.app.repository.entity.Product;
import com.metro.app.service.request.product.ProductRequest;
import com.metro.app.service.response.PageResult;
import com.metro.app.service.response.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     *  Stores product to database product table.
     * @param productRequest instance of ProductRequest
     * @return instance of ProductResponse
     */
    public ProductResponse creatProduct(final ProductRequest productRequest) {
        final Product product = productRepository.save(new Product(productRequest.getName(), productRequest.getPrice()));
        return new ProductResponse(product.getId(), product.getName(), product.getPrice());
    }

    /**
     * Based on id(product id) and data in ProductRequest updated data of product in DB.
     * @param id identifier of Product in DB - primary key
     * @param productRequest instance of ProductRequest
     * @return instance of ProductResponse
     */
    @Transactional
    public ProductResponse updateProduct(final Long id, final ProductRequest productRequest) {
        final Optional<Product> optProduct = productRepository.findById(id);
        if(optProduct.isPresent()) {
            Product product = optProduct.get();
            product.setName(productRequest.getName());
            product.setPrice(productRequest.getPrice());
            product = productRepository.save(product);
            return new ProductResponse(product.getId(), product.getName(), product.getPrice());
        } else {
            throw new ResourceNotFoundException("Product not found with id=" + id);
        }
    }

    /**
     * Returns a page of ProductResponse-s based on page and size parameter.
     * @param page  0+
     * @param size wanted size
     * @return paged  list of ProductResponse
     */
    public PageResult<ProductResponse> getProducts(final int page, final int size) {
        final Page<Product> pageObject = productRepository.findAll(PageRequest.of(page, size));
        final List<ProductResponse> productResponses = new ArrayList<>(pageObject.getSize());
        for(final Product product : pageObject.getContent()) {
            final ProductResponse productResponse = new ProductResponse(product.getId(), product.getName(), product.getPrice());
            productResponses.add(productResponse);
        }
        return new PageResult<>(pageObject.getTotalPages(), productResponses);
    }
}
