package com.metro.app.repository;

import com.metro.app.repository.OrderItem;
import com.metro.app.repository.Product;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderItemTest {
    @Test
    public void equals() {
        final double price = 20.1;
        final String productName = "product name";
        final Product product = new Product(productName, price);
        product.setId(2L);
        final double quantity = 20.1;
        final OrderItem orderItem = new OrderItem(product, quantity);
        assertFalse(orderItem.equals(null));
        assertFalse(orderItem.equals(new Object()));
        assertTrue(orderItem.equals(orderItem));
        OrderItem orderItem1 = new OrderItem(product, quantity);
        assertTrue(orderItem.equals(orderItem1));
        orderItem1 = new OrderItem(product, 19.9);
        orderItem.setId(1L);
        orderItem1.setId(1L);
        assertTrue(orderItem.equals(orderItem1));
    }
}
