package com.metro.app.service.model.resource.order;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public class OrderResource<T> {
    @Schema(name = "email",
            type = "string",
            description = "Buyer email",
            required = true,
            example = "buyer@google.com")
    @NotBlank(message = "Email field can't be empty")
    private String email;
    @Size(min = 1)
    private List<T> orderItemsResource;

    protected OrderResource() {

    }

    public OrderResource(final String email, final List<T> orderItemsResource) {
        this.email = email;
        this.orderItemsResource = orderItemsResource;
    }

    public String getEmail() {
        return email;
    }

    public List<T> getOrderItemsResource() {
        return orderItemsResource;
    }
}
