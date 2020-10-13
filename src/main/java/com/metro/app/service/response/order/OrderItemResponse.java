package com.metro.app.service.response.order;

import com.metro.app.service.request.order.OrderItemRequest;
import io.swagger.v3.oas.annotations.media.Schema;

public class OrderItemResponse extends OrderItemRequest {
    @Schema(name = "price",
            type = "number",
            format = "double",
            description = "Product price in moment of purchase",
            example = "127.0")
    private Double price;

    protected OrderItemResponse() {

    }

    public OrderItemResponse(final Long productId, final Double price, final Double quantity) {
        super(productId, quantity);
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
}
