package com.metro.app.service;

import com.metro.app.error.ResourceNotFoundException;
import com.metro.app.jpa.model.Order;
import com.metro.app.jpa.model.OrderItem;
import com.metro.app.jpa.model.Product;
import com.metro.app.jpa.repository.OrderRepository;
import com.metro.app.jpa.repository.ProductRepository;
import com.metro.app.service.model.PageResult;
import com.metro.app.service.model.resource.order.OrderItemResource;
import com.metro.app.service.model.resource.order.OrderResource;
import com.metro.app.service.model.view.order.OrderItemView;
import com.metro.app.service.model.view.order.OrderView;
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

    public PageResult<OrderView<OrderItemView>> getOrders(final Date from, final Date to, final int page, final int size) {
        final Page<Order> pageObject = orderRepository.findWithinPeriod(from, to, PageRequest.of(page, size));
        final List<OrderView<OrderItemView>> orderResources = new ArrayList<>(pageObject.getSize());
        for(final Order order : pageObject.getContent()) {
            orderResources.add(createOrderResource(order));
        }
        return new PageResult<>(pageObject.getTotalPages(), orderResources);
    }

    public OrderView<OrderItemView> placeOrder(final OrderResource<OrderItemResource> orderResource) {
        final Order order = new Order(orderResource.getEmail(), new Date());
        for(final OrderItemResource orderItemResource : orderResource.getOrderItemsResource()) {
            final Optional<Product> product = productRepository.findById(orderItemResource.getProductId());
            if(product.isPresent()) {
                order.addOrderItem(new OrderItem(product.get(), orderItemResource.getQuantity()));
            } else {
                throw new ResourceNotFoundException("Product not found with id=" + orderItemResource.getProductId());
            }
        }
        return createOrderResource(orderRepository.save(order));
    }

    private OrderView<OrderItemView> createOrderResource(final Order order) {
        double totalCost = 0.0;
        final List<OrderItemView> orderItemViews = new ArrayList<>();
        for(final OrderItem orderItem : order.getOrderItems()) {
            final OrderItemView orderItemView = new OrderItemView(orderItem.getProduct().getId(), orderItem.getPrice(),
                                                                  orderItem.getQuantity());
            totalCost += orderItemView.getPrice() * orderItemView.getQuantity();
            orderItemViews.add(orderItemView);
        }
        final OrderView<OrderItemView> orderView = new OrderView(order.getId(), totalCost, order.getEmail(), order.getDtc(),
                                                                 orderItemViews);
        return orderView;
    }
}
