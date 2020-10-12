package com.metro.app.controller;

import com.metro.app.service.ProductService;
import com.metro.app.service.model.PageResult;
import com.metro.app.service.model.request.product.ProductRequest;
import com.metro.app.service.model.view.product.ProductView;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@RestController
public class ProductController {
    public static final String PRODUCT = "/product";
    public static final String PRODUCT_FOR_ID = "/product/{id}";
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = PRODUCT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductView> createProduct(@Valid @RequestBody final ProductRequest productRequest) {
        return ResponseEntity.ok(productService.creatProduct(productRequest));
    }

    @PutMapping(value = PRODUCT_FOR_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductView> updateProduct(@PathVariable("id") final Long id,
                                                         @Valid @RequestBody final ProductRequest productRequest) {
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    @GetMapping(value = PRODUCT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResult<ProductView>> getProducts(@PositiveOrZero @RequestParam("page") final int page,
                                                               @Positive @RequestParam("size") final int size) {
        return ResponseEntity.ok(productService.getProducts(page, size));
    }
}
