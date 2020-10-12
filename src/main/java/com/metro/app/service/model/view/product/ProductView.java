package com.metro.app.service.model.view.product;

import com.metro.app.service.model.request.product.ProductRequest;
import io.swagger.v3.oas.annotations.media.Schema;

public class ProductView extends ProductRequest {
    @Schema(name = "id",
            type = "number",
            format = "integer",
            description = "Product identifier",
            example = "666")
    private Long id;

    protected ProductView() {

    }

    public ProductView(final Long id, final String name, final Double price) {
        super(name, price);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
