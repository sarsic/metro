package com.metro.app.service.model.resource.product;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class ProductResource {
    @Schema(name = "name",
            type = "string",
            description = "Product name",
            required = true,
            example = "chair")
    @NotBlank(message = "This field can't be empty")
    private String name;
    @Schema(name = "price",
            type = "number",
            format = "double",
            description = "Product unit price",
            required = true,
            example = "127.0")
    @DecimalMin(value = "0.0", message = "Price min 0.0")
    private Double price;

    protected ProductResource() {

    }

    public ProductResource(final String name, final Double price) {
        this.name = name;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductResource that = (ProductResource) o;
        return Objects.equals(name, that.name) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
