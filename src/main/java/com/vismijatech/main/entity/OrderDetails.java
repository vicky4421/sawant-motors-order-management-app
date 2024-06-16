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
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    // bidirectional relation between order and order details
    @OneToOne(mappedBy = "orderDetails", cascade = CascadeType.ALL)
    @JsonIgnore
    private Order order;

    // bidirectional relation between product and order details
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
            joinColumns = @JoinColumn(name = "order_details_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productList;

    // constructors
    public OrderDetails(int quantity) {
        this.quantity = quantity;
    }

    // convenience methods to add products
    public void addProduct(Product product) {
        if (productList == null) productList = new ArrayList<>();
        productList.add(product);
    }
}
