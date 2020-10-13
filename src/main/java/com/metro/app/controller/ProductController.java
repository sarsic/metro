package com.metro.app.controller;

import com.metro.app.service.response.PageResult;
import com.metro.app.service.ProductService;
import com.metro.app.service.request.product.ProductRequest;
import com.metro.app.service.response.product.ProductResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Tag(name = ProductController.PRODUCTS_ENDPOINT)
@RequestMapping(ProductController.PRODUCTS_ENDPOINT)
@RestController
public class ProductController {
    public static final String PRODUCTS_ENDPOINT = "/products";
    public static final String PRODUCTS_ID = "{id}";
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@Valid @RequestBody final ProductRequest productRequest) {
        return productService.creatProduct(productRequest);
    }

    @PutMapping(value = PRODUCTS_ID, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable("id") final Long id,
                                         @Valid @RequestBody final ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PageResult<ProductResponse> getProducts(@PositiveOrZero @RequestParam("page") final int page,
                                                                   @Positive @RequestParam("size")  @Max(100) final int size) {
        return productService.getProducts(page, size);
    }
}
