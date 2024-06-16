package com.vismijatech.main.entity;

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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String partNumber;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    @JoinTable(
            name = "order_details_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_details_id")
    )
    private List<OrderDetails> orderDetailsList;

    // constructors
    public Product(String name, String partNumber) {
        this.name = name;
        this.partNumber = partNumber;
    }

    // convenience methods to add order details
    public void addOrderDetails(OrderDetails orderDetails) {
        if (orderDetailsList == null) orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetails);
    }
}
