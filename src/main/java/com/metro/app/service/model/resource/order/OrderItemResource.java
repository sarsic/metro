package com.metro.app.service.model.resource.order;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Positive;

public class OrderItemResource {
    @Schema(name = "productId",
            type = "number",
            format = "integer",
            description = "Wanted product identifier",
            required = true,
            example = "2121")
    @Positive
    protected Long productId;
    @Schema(name = "quantity",
            type = "number",
            format = "double",
            description = "Wanted quantity of selected product",
            required = true,
            example = "1.0")
    @DecimalMin(value = "0.0", message = "Quantity min 0.0")
    protected Double quantity;

    protected OrderItemResource() {

    }

    public OrderItemResource(final Long productId, final Double quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Double getQuantity() {
        return quantity;
    }
}
