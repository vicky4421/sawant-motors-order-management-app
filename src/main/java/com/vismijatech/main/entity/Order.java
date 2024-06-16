package com.vismijatech.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date orderDate;

    // bidirectional relation between order and party
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "party_id")
    private Party party;

    // Constructors
    public Order(String name, Date orderDate) {
        this.name = name;
        this.orderDate = orderDate;
    }

    public Order(String name, Date orderDate, Party party) {
        this.name = name;
        this.orderDate = orderDate;
        this.party = party;
    }
}
