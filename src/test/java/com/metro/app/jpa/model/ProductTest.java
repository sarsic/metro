package com.metro.app.jpa.model;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductTest {
    @Test
    public void equals() {
        final double price = 20.1;
        final String productName = "product name";
        final Product product = new Product(productName, price);
        assertFalse(product.equals(null));
        assertFalse(product.equals(new Object()));
        assertTrue(product.equals(product));
        Product product1 = new Product(productName, price);
        assertTrue(product.equals(product1));
        product1 = new Product("product name2", price);
        product.setId(1L);
        product1.setId(1L);
        assertTrue(product.equals(product1));
    }
}
