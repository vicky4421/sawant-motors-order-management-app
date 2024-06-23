package com.vismijatech.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String whatsappNumber;

    @Column(unique = true)
    private String alternateNumber;

    // bidirectional relation between order and party
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orderList;

    // Constructors

    public Supplier(Long id, String name, String whatsappNumber, String alternateNumber) {
        this.id = id;
        this.name = name;
        this.whatsappNumber = whatsappNumber;
        this.alternateNumber = alternateNumber;
    }
    public Supplier(String name, String whatsappNumber, String alternateNumber) {
        this.name = name;
        this.whatsappNumber = whatsappNumber;
        this.alternateNumber = alternateNumber;
    }

    public Supplier(String name, String whatsappNumber) {
        this.name = name;
        this.whatsappNumber = whatsappNumber;
    }

    // convenience method for bidirectional relation between party and order
    public void addOrder(Order order) {
        if (orderList == null) orderList = new ArrayList<>();
        orderList.add(order);
        order.setSupplier(this);
    }
}
