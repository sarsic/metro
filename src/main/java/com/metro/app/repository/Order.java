package com.metro.app.repository;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @SequenceGenerator(name = "seq_orders", sequenceName = "seq_orders", allocationSize = 1) @Id
    @GeneratedValue(generator = "seq_orders") private Long id;
    private String email;
    @Temporal(TemporalType.TIMESTAMP) private Date dtc;
    @OneToMany(mappedBy = "order",
               cascade = {CascadeType.ALL},
               fetch = FetchType.EAGER,
               orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    protected Order() {
    }

    public Order(final String email, final Date dtc) {
        this.email = email;
        this.dtc = dtc;
    }

    public Date getDtc() {
        return dtc;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setDtc(final Date create) {
        this.dtc = create;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setEmail(final String title) {
        this.email = title;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(final List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    public void addOrderItem(final OrderItem item) {
        this.orderItems.add(item);
        item.setOrder(this);
    }

    @Override
    public boolean equals(final Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }
        final Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
