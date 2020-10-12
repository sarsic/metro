package com.metro.app.service;

import com.metro.app.error.ResourceNotFoundException;
import com.metro.app.jpa.model.Product;
import com.metro.app.jpa.repository.ProductRepository;
import com.metro.app.service.model.PageResult;
import com.metro.app.service.model.resource.product.ProductResource;
import com.metro.app.service.model.view.product.ProductView;
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

    public ProductView creatProduct(final ProductResource productResource) {
        final Product product = productRepository.save(new Product(productResource.getName(), productResource.getPrice()));
        return new ProductView(product.getId(), product.getName(), product.getPrice());
    }

    @Transactional
    public ProductView updateProduct(final Long id, final ProductResource productResource) {
        final Optional<Product> optProduct = productRepository.findById(id);
        if(optProduct.isPresent()) {
            Product product = optProduct.get();
            product.setName(productResource.getName());
            product.setPrice(productResource.getPrice());
            product = productRepository.save(product);
            return new ProductView(product.getId(), product.getName(), product.getPrice());
        } else {
            throw new ResourceNotFoundException("Product not found with id=" + id);
        }
    }

    public PageResult<ProductView> getProducts(final int page, final int size) {
        final Page<Product> pageObject = productRepository.findAll(PageRequest.of(page, size));
        final List<ProductView> productViews = new ArrayList<>(pageObject.getSize());
        for(final Product product : pageObject.getContent()) {
            final ProductView productView = new ProductView(product.getId(), product.getName(), product.getPrice());
            productViews.add(productView);
        }
        return new PageResult<>(pageObject.getTotalPages(), productViews);
    }
}
