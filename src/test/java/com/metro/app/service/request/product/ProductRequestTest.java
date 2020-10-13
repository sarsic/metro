package com.metro.app.service.request.product;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductRequestTest {
    @Test
    public void equals() {
        final ProductRequest productRequest = new ProductRequest("product name", 2.2);
        assertFalse(productRequest.equals(null));
        assertFalse(productRequest.equals(new Object()));
        assertTrue(productRequest.equals(productRequest));
         ProductRequest productRequest2 = new ProductRequest("product name", 2.2);
        assertTrue(productRequest.equals(productRequest2));
        productRequest2 = new ProductRequest("product name2", 2.2);
        assertFalse(productRequest.equals(productRequest2));
        productRequest2 = new ProductRequest("product name", 2.1);
        assertFalse(productRequest.equals(productRequest2));
    }
}
