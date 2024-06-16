package com.vismijatech.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

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
    @JoinColumn(name = "party_id")
    private Party party;

    // bidirectional relation between order and order details
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_detail_id")
    private OrderDetails orderDetails;

    // Constructors
    public Order(String name, LocalDate orderDate) {
        this.name = name;
        this.orderDate = orderDate;
    }
}
