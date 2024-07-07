package com.vismijatech.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    // bidirectional relation between order and order details
    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    // bidirectional relation between product and order details
    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // constructors
    public OrderDetails(int quantity) {
        this.quantity = quantity;
    }
}
