package com.metro.app.repository;

import com.metro.app.repository.Order;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OrderTest {
    @Test
    public void equals() {
        final Date dtc = new Date();
        final String email = "email@test.com";
        final Order order = new Order(email, dtc);
        assertFalse(order.equals(null));
        assertFalse(order.equals(new Object()));
        assertTrue(order.equals(order));
        Order order1 = new Order(email, dtc);
        assertTrue(order.equals(order1));
        order1 = new Order("product name2", new Date());
        order.setId(1L);
        order1.setId(1L);
        assertTrue(order.equals(order1));
    }
}
