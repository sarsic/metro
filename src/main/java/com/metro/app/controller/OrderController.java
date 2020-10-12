package com.metro.app.controller;

import com.metro.app.service.OrderService;
import com.metro.app.service.model.PageResult;
import com.metro.app.service.model.request.order.OrderItemRequest;
import com.metro.app.service.model.request.order.OrderRequest;
import com.metro.app.service.model.view.order.OrderItemView;
import com.metro.app.service.model.view.order.OrderView;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@RestController
public class OrderController {
    public static final String ORDER = "/order";
    public static final String FROM_DATE = "from";
    public static final String TO_DATE = "to";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = ORDER, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderView<OrderItemView>> placeOrder(@Valid @RequestBody final OrderRequest<OrderItemRequest> orderRequest) {
        return ResponseEntity.ok(orderService.placeOrder(orderRequest));
    }

    @GetMapping(value = ORDER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResult<OrderView<OrderItemView>>> getOrders(
            @Schema(name = FROM_DATE, type = "string", format = "date", required = true, example = "1970-01-01")
            @RequestParam(value = FROM_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date from,
            @Schema(name = TO_DATE, type = "string", format = "date", required = true, example = "1970-01-01")
            @RequestParam(value = TO_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date to,
            @PositiveOrZero @RequestParam(PAGE) final int page, @Positive @RequestParam(SIZE) final int size) {
        return ResponseEntity.ok(orderService.getOrders(from, to, page, size));
    }
}
