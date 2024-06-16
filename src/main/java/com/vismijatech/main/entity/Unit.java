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
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortName;

    @OneToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            },
            mappedBy = "unit"
    )
    private List<Product> productList;

    // constructors
    public Unit(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    // convenience methods to add product
    public void addProduct(Product product) {
        if (productList == null) productList = new ArrayList<>();
        productList.add(product);
    }
}
