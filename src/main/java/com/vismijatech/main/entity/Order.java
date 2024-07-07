package com.vismijatech.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate orderDate;

    // bidirectional relation between order and party
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    // bidirectional relation between order and order details
    @OneToMany( mappedBy = "order",
            cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    private List<OrderDetails> orderDetailsList;

    // Constructors
    public Order(String name, LocalDate orderDate) {
        this.name = name;
        this.orderDate = orderDate;
    }

    // convenience methods
    public void addOrderDetails(OrderDetails orderDetail){
        if (orderDetailsList == null) orderDetailsList= new ArrayList<>();
        orderDetailsList.add(orderDetail);
    }

    public void addSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
