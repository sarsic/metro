package com.metro.app.service.model.view.order;

import com.metro.app.service.model.resource.order.OrderItemResource;
import io.swagger.v3.oas.annotations.media.Schema;

public class OrderItemView extends OrderItemResource {
    @Schema(name = "price",
            type = "number",
            format = "double",
            description = "Product price in moment of purchase",
            example = "127.0")
    private Double price;

    protected OrderItemView() {

    }

    public OrderItemView(final Long productId, final Double price, final Double quantity) {
        super(productId, quantity);
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
}
