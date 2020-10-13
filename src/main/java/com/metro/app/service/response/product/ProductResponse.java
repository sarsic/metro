package com.metro.app.service.response.product;

import com.metro.app.service.request.product.ProductRequest;
import io.swagger.v3.oas.annotations.media.Schema;

public class ProductResponse extends ProductRequest {
    @Schema(name = "id",
            type = "number",
            format = "integer",
            description = "Product identifier",
            example = "666")
    private Long id;

    protected ProductResponse() {

    }

    public ProductResponse(final Long id, final String name, final Double price) {
        super(name, price);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
