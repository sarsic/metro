package com.metro.app.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metro.app.exception.RestExceptionHandler;
import com.metro.app.service.OrderService;
import com.metro.app.service.response.PageResult;
import com.metro.app.service.request.order.OrderItemRequest;
import com.metro.app.service.request.order.OrderRequest;
import com.metro.app.service.response.order.OrderItemResponse;
import com.metro.app.service.response.order.OrderResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest {
    @Mock OrderService orderService;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService)).setControllerAdvice(
                new RestExceptionHandler()).setMessageConverters(converter).build();
    }

    @Test
    public void testGetOrders() throws Exception {
        final String dateFrom = "2018-01-01";
        final int size = 10;
        final int page = 0;
        final String dateTo = "2019-01-01";
        final OrderResponse<OrderItemResponse> orderResource = createOrderView();
        final PageResult<OrderResponse<OrderItemResponse>> expected = new PageResult<>(1L, Collections.singletonList(orderResource));
        when(orderService.getOrders(any(), any(), anyInt(), anyInt())).thenReturn(expected);
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(OrderController.ORDERS_ENDPOINT)
                                                                       .param(OrderController.FROM_DATE, dateFrom)
                                                                       .param(OrderController.TO_DATE, dateTo)
                                                                       .param(OrderController.PAGE, String.valueOf(page))
                                                                       .param(OrderController.SIZE, String.valueOf(size)))
                                        .andExpect(status().isOk())
                                        .andReturn();
        final PageResult<OrderResponse<OrderItemResponse>> actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                                                                                           new TypeReference<PageResult<OrderResponse<OrderItemResponse>>>() {});
        assertEquals(expected.getTotalPages(), actual.getTotalPages());
        assertEquals(expected.getItems().size(), actual.getItems().size());
        assertEquals(expected.getItems().get(0).getId(), actual.getItems().get(0).getId());
        assertEquals(expected.getItems().get(0).getEmail(), actual.getItems().get(0).getEmail());
        assertEquals(expected.getItems().get(0).getDtc(), actual.getItems().get(0).getDtc());
        assertEquals(expected.getItems().get(0).getTotalCost(), actual.getItems().get(0).getTotalCost());
        assertEquals(objectMapper.writeValueAsString(expected.getItems().get(0)),
                     objectMapper.writeValueAsString(actual.getItems().get(0)));
        assertEquals(expected.getItems().get(0).getOrderItemsResource().get(0).getPrice(), actual.getItems()
                                                                                                 .get(0)
                                                                                                 .getOrderItemsResource()
                                                                                                 .get(0)
                                                                                                 .getPrice());
        assertEquals(expected.getItems().get(0).getOrderItemsResource().get(0).getProductId(), actual.getItems()
                                                                                                     .get(0)
                                                                                                     .getOrderItemsResource()
                                                                                                     .get(0)
                                                                                                     .getProductId());
        assertEquals(expected.getItems().get(0).getOrderItemsResource().get(0).getQuantity(), actual.getItems()
                                                                                                    .get(0)
                                                                                                    .getOrderItemsResource()
                                                                                                    .get(0)
                                                                                                    .getQuantity());
    }

    @Test
    public void testPlaceOrder() throws Exception {
        final OrderRequest<OrderItemRequest> orderRequest = createOrderResource();
        final OrderResponse<OrderItemResponse> expected = createOrderView();
        when(orderService.placeOrder(any())).thenReturn(expected);
        final MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(OrderController.ORDERS_ENDPOINT)
                                                                       .contentType(MediaType.APPLICATION_JSON)
                                                                       .content(objectMapper.writeValueAsBytes(orderRequest))
                                                                       .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(
                status().isCreated()).andReturn();
        final OrderResponse<OrderItemResponse> actual = objectMapper.readValue(result.getResponse().getContentAsByteArray(),
                                                                               new TypeReference<OrderResponse<OrderItemResponse>>() {});
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getDtc(), actual.getDtc());
        assertEquals(expected.getTotalCost(), actual.getTotalCost());
        assertEquals(objectMapper.writeValueAsString(expected.getOrderItemsResource().get(0)),
                     objectMapper.writeValueAsString(actual.getOrderItemsResource().get(0)));
        assertEquals(expected.getOrderItemsResource().get(0).getPrice(), actual.getOrderItemsResource().get(0).getPrice());
        assertEquals(expected.getOrderItemsResource().get(0).getProductId(),
                     actual.getOrderItemsResource().get(0).getProductId());
        assertEquals(expected.getOrderItemsResource().get(0).getQuantity(), actual.getOrderItemsResource().get(0).getQuantity());
    }

    private OrderRequest<OrderItemRequest> createOrderResource() {
        final String email = "test@test.com";
        final List<OrderItemRequest> orderItemsResource = new ArrayList<>();
        final Long productId = 2L;
        final Double quantity = 2.0;
        orderItemsResource.add(new OrderItemRequest(productId, quantity));
        final OrderRequest<OrderItemRequest> orderRequest = new OrderRequest(email, orderItemsResource);
        return orderRequest;
    }

    private OrderResponse<OrderItemResponse> createOrderView() {
        final Long id = 1L;
        final Double totalCos = 50.2;
        final String email = "test@test.com";
        final List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        final Long productId = 2L;
        final Double price = 25.1;
        final Double quantity = 2.0;
        orderItemResponses.add(new OrderItemResponse(productId, price, quantity));
        final OrderResponse<OrderItemResponse> orderResponse = new OrderResponse(id, totalCos, email, new Date(), orderItemResponses);
        return orderResponse;
    }
}
