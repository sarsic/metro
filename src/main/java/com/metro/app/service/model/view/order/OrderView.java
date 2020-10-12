package com.metro.app.service.model.view.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.metro.app.service.model.request.order.OrderRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

public class OrderView<T> extends OrderRequest<T> {
    @Schema(name = "id",
            type = "number",
            format = "integer",
            description = "Order identifier",
            example = "888")
    private Long id;
    @Schema(name = "totalCost",
            type = "number",
            format = "double",
            description = "Total cost of an order",
            example = "127.0")
    private Double totalCost;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") private Date dtc;

    protected OrderView() {

    }

    public OrderView(final Long id, final Double totalCost, final String email, final Date dtc,
                     final List<T> orderItemsResource) {
        super(email, orderItemsResource);
        this.id = id;
        this.totalCost = totalCost;
        this.dtc = dtc;
    }

    public Long getId() {
        return id;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public Date getDtc() {
        return dtc;
    }
}
