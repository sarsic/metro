package com.metro.app.jpa.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {
    @SequenceGenerator(name = "seq_products", sequenceName = "seq_products", allocationSize = 1 )
    @Id
    @GeneratedValue(generator = "seq_products" )
    private Long id;
    private String name;
    private Double price;

    protected Product() {
    }

    public Product(final String name, final Double price) {
        this.name = name;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String title) {
        this.name = title;
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        final Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
