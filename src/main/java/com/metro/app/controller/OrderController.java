package com.metro.app.controller;

import com.metro.app.service.OrderService;
import com.metro.app.service.PageResult;
import com.metro.app.service.request.order.OrderItemRequest;
import com.metro.app.service.request.order.OrderRequest;
import com.metro.app.service.response.order.OrderItemResponse;
import com.metro.app.service.response.order.OrderResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;

@Tag(name = OrderController.ORDERS_ENDPOINT)
@RequestMapping(OrderController.ORDERS_ENDPOINT)
@RestController
public class OrderController {
    public static final String ORDERS_ENDPOINT = "orders";
    public static final String FROM_DATE = "from";
    public static final String TO_DATE = "to";
    public static final String PAGE = "page";
    public static final String SIZE = "size";
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse<OrderItemResponse> placeOrder(@Valid @RequestBody final OrderRequest<OrderItemRequest> orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PageResult<OrderResponse<OrderItemResponse>> getOrders(
            @Schema(name = FROM_DATE, type = "string", format = "date", required = true, example = "1970-01-01")
            @RequestParam(value = FROM_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date from,
            @Schema(name = TO_DATE, type = "string", format = "date", required = true, example = "1970-01-01")
            @RequestParam(value = TO_DATE) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date to,
            @PositiveOrZero @RequestParam(PAGE) final int page, @Positive @RequestParam(SIZE) final int size) {
        return orderService.getOrders(from, to, page, size);
    }
}
