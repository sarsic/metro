package com.metro.app.service;

import com.metro.app.exception.ResourceNotFoundException;
import com.metro.app.repository.entity.Order;
import com.metro.app.repository.entity.OrderItem;
import com.metro.app.repository.entity.Product;
import com.metro.app.repository.OrderRepository;
import com.metro.app.repository.ProductRepository;
import com.metro.app.service.request.order.OrderItemRequest;
import com.metro.app.service.request.order.OrderRequest;
import com.metro.app.service.response.PageResult;
import com.metro.app.service.response.order.OrderItemResponse;
import com.metro.app.service.response.order.OrderResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class OrderServiceTest {
    @Mock private ProductRepository productRepository;
    @Mock private OrderRepository orderRepository;
    private OrderService orderService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(orderRepository, productRepository);
    }

    @Test
    public void getOrders() {
        final Date from = new Date();
        final Date to = new Date();
        final int page = 0;
        final int size = 1;
        final Product product = getProduct();
        final OrderItem orderItem = getOrderItem(product);
        final Order order = getOrder();
        order.setId(214L);
        order.addOrderItem(orderItem);
        final List<Order> orders = Collections.singletonList(order);
        final Page<Order> orderPage = new PageImpl<>(orders, PageRequest.of(page, size), orders.size());
        when(orderRepository.findWithinPeriod(from, to, PageRequest.of(page, size))).thenReturn(orderPage);
        final PageResult<OrderResponse<OrderItemResponse>> actual = orderService.getOrders(from, to, page, size);
        assertEquals(1, actual.getTotalPages());
        assertEquals(1, actual.getItems().size());
        assertEquals(order.getDtc(), actual.getItems().get(0).getDtc());
        assertTrue(orderItem.getQuantity() * orderItem.getPrice() == actual.getItems().get(0).getTotalCost().doubleValue());
        assertEquals(order.getId(), actual.getItems().get(0).getId());
        assertEquals(1, actual.getItems().get(0).getOrderItemsResource().size());
        assertEquals(product.getPrice(), actual.getItems().get(0).getOrderItemsResource().get(0).getPrice());
        assertEquals(product.getId(), actual.getItems().get(0).getOrderItemsResource().get(0).getProductId());
    }

    private Order getOrder() {
        final String email = "test@gmail.com";
        final Date dtc = new Date();
        return new Order(email, dtc);
    }

    private OrderItem getOrderItem(final Product product) {
        final Double quantity = 2.0;
        return new OrderItem(product, quantity);
    }

    private Product getProduct() {
        final String name = "product";
        final Double price = 201.1;
        final Product product = new Product(name, price);
        final Long productId = 214L;
        product.setId(productId);
        return product;
    }

    @Test
    public void placeOrder() {
        final Product product = getProduct();
        final OrderItem orderItem = getOrderItem(product);
        final Order order = getOrder();
        order.setId(214L);
        order.addOrderItem(orderItem);
        final Optional<Product> productOptional = Optional.of(product);
        final List<OrderItemRequest> orderItemsResource = new ArrayList<>();
        orderItemsResource.add(new OrderItemRequest(product.getId(), order.getOrderItems().get(0).getQuantity()));
        final OrderRequest<OrderItemRequest> orderRequest = new OrderRequest<>(order.getEmail(), orderItemsResource);
        when(productRepository.findById(product.getId())).thenReturn(productOptional);
        when(orderRepository.save(any())).thenReturn(order);
        final OrderResponse<OrderItemResponse> actual = orderService.placeOrder(orderRequest);
        assertEquals(order.getId(), actual.getId());
        assertEquals(order.getDtc(), actual.getDtc());
        assertEquals(order.getOrderItems().size(), actual.getOrderItemsResource().size());
        assertTrue(order.getOrderItems().get(0).getPrice() * order.getOrderItems().get(0).getQuantity() ==
                   actual.getTotalCost().doubleValue());
        assertEquals(order.getOrderItems().get(0).getPrice(), actual.getOrderItemsResource().get(0).getPrice());
        assertEquals(order.getOrderItems().get(0).getQuantity(), actual.getOrderItemsResource().get(0).getQuantity());
        assertEquals(order.getOrderItems().get(0).getProduct().getId(), actual.getOrderItemsResource().get(0).getProductId());
    }

    @Test
    public void placeOrderNotFoundProduct() {
        final Product product = getProduct();
        final OrderItem orderItem = getOrderItem(product);
        final Order order = getOrder();
        order.setId(214L);
        order.addOrderItem(orderItem);
        final List<OrderItemRequest> orderItemsResource = new ArrayList<>();
        orderItemsResource.add(new OrderItemRequest(product.getId(), order.getOrderItems().get(0).getQuantity()));
        final OrderRequest<OrderItemRequest> orderRequest = new OrderRequest<>(order.getEmail(), orderItemsResource);
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        final ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            orderService.placeOrder(orderRequest);
        });
        assertEquals("Product not found with id=" + product.getId(), exception.getMessage());
    }
}
