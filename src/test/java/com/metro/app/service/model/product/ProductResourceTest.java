package com.metro.app.service.model.product;

import com.metro.app.service.model.resource.product.ProductResource;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductResourceTest {
    @Test
    public void equals() {
        final ProductResource productResource = new ProductResource("product name", 2.2);
        assertFalse(productResource.equals(null));
        assertFalse(productResource.equals(new Object()));
        assertTrue(productResource.equals(productResource));
         ProductResource productResource2 = new ProductResource("product name", 2.2);
        assertTrue(productResource.equals(productResource2));
        productResource2 = new ProductResource("product name2", 2.2);
        assertFalse(productResource.equals(productResource2));
        productResource2 = new ProductResource("product name", 2.1);
        assertFalse(productResource.equals(productResource2));
    }
}
