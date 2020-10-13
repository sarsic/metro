package com.metro.app.repository.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @SequenceGenerator(name = "seq_order_items", sequenceName = "seq_order_items", allocationSize = 1 )
    @Id
    @GeneratedValue(generator = "seq_order_items" )
    private Long id;
    @ManyToOne( fetch = FetchType.LAZY )
    private Order order;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id" )
    private Product product;
    private Double price;
    private Double quantity;

    protected OrderItem() {
    }

    public OrderItem(final Product product,final Double quantity) {
        this.product = product;
        this.price = product.getPrice();
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(final Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(final Product product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(final Double quant) {
        this.quantity = quant;
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderItem order = (OrderItem) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
