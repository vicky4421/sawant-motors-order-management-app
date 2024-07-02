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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String partNumber;

    // bidirectional one-to-many association to OrderDetails
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

    // bidirectional many-to-one association to Unit
    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    // bidirectional many-to-one association to ProductCategory
    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    @JoinColumn(name = "category_id", nullable = false)
    private ProductCategory category;

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

    // convenience methods to add unit
    public void addUnit(Unit unit) {
        this.unit = unit;
    }
}
