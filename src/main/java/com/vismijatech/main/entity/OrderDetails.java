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
    private int quantity;

    // bidirectional relation between order and order details
    @OneToOne(mappedBy = "orderDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private Order order;

    // constructors
    public OrderDetails(int quantity) {
        this.quantity = quantity;
    }
}
