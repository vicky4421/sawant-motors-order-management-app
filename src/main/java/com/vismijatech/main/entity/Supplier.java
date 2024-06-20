package com.vismijatech.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // bidirectional relation between party and contact number
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<ContactNumber> contactNumbersList;

    // bidirectional relation between order and party
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orderList;

    // Constructors
    public Supplier(String name) {
        this.name = name;
    }

    // convenience method for bidirectional relation between party and contact number
    public void addContactNumber(ContactNumber contactNumber) {
        if (contactNumbersList == null) contactNumbersList = new ArrayList<>();
        contactNumbersList.add(contactNumber);
        contactNumber.setSupplier(this);
    }

    // convenience method for bidirectional relation between party and order
    public void addOrder(Order order) {
        if (orderList == null) orderList = new ArrayList<>();
        orderList.add(order);
        order.setSupplier(this);
    }
}
