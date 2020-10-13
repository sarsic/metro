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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(final OrderRepository orderRepository, final ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    /**
     * Returns paged List of orders.
     * @param from Date
     * @param to Date
     * @param page 0+
     * @param size wanted size
     * @return  paged  list of OrderResponse
     */
    public PageResult<OrderResponse<OrderItemResponse>> getOrders(final Date from, final Date to, final int page, final int size) {
        final Page<Order> pageObject = orderRepository.findWithinPeriod(from, to, PageRequest.of(page, size));
        final List<OrderResponse<OrderItemResponse>> orderResponse = new ArrayList<>(pageObject.getSize());
        for(final Order order : pageObject.getContent()) {
            orderResponse.add(createOrderResponse(order));
        }
        return new PageResult<>(pageObject.getTotalPages(), orderResponse);
    }

    public OrderResponse<OrderItemResponse> placeOrder(final OrderRequest<OrderItemRequest> orderRequest) {
        final Order order = new Order(orderRequest.getEmail(), new Date());
        for(final OrderItemRequest orderItemRequest : orderRequest.getOrderItemsResource()) {
            final Optional<Product> product = productRepository.findById(orderItemRequest.getProductId());
            if(product.isPresent()) {
                order.addOrderItem(new OrderItem(product.get(), orderItemRequest.getQuantity()));
            } else {
                throw new ResourceNotFoundException("Product not found with id=" + orderItemRequest.getProductId());
            }
        }
        return createOrderResponse(orderRepository.save(order));
    }

    private OrderResponse<OrderItemResponse> createOrderResponse(final Order order) {
        double totalCost = 0.0;
        final List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for(final OrderItem orderItem : order.getOrderItems()) {
            final OrderItemResponse orderItemResponse = new OrderItemResponse(orderItem.getProduct().getId(), orderItem.getPrice(),
                                                                              orderItem.getQuantity());
            totalCost += orderItemResponse.getPrice() * orderItemResponse.getQuantity();
            orderItemResponses.add(orderItemResponse);
        }
        return new OrderResponse(order.getId(), totalCost, order.getEmail(), order.getDtc(),
                                                                                 orderItemResponses);
    }
}
