package com.vismijatech.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String orderDate;

    // bidirectional relation between order and party
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "supplier_id", nullable = false)
    @JsonIgnore
    private Supplier supplier;

    // bidirectional relation between order and order details
    @OneToMany( mappedBy = "order",
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JsonIgnore
    private List<OrderDetails> orderDetailsList = new ArrayList<>();

    // Constructors
    public Order(String name, String orderDate) {
        this.name = name;
        this.orderDate = orderDate;
    }

    // convenience methods
    public void addOrderDetails(OrderDetails orderDetail){
        orderDetailsList.add(orderDetail);
        orderDetail.setOrder(this);
    }

    public void addSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
