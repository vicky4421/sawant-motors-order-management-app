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
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // bidirectional one-to-many association to Product
    @OneToMany(
            mappedBy = "category",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.DETACH,
                    CascadeType.REFRESH
            }
    )
    private List<Product> productList;

    // Self-referencing relationship for parent category
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ProductCategory parentCategory;

    // Self-referencing relationship for child categories
    @OneToMany(mappedBy = "parentCategory")
    private List<ProductCategory> childCategories;

    // constructors
    public ProductCategory(String name) {
        this.name = name;
    }

    // convenience methods to add product
    public void addProduct(Product product) {
        if (productList == null) productList = new ArrayList<>();
        productList.add(product);
        product.setCategory(this);
    }

    // Convenience method to add a child category
    public void addChildCategory(ProductCategory childCategory) {
        if (childCategories == null) childCategories = new ArrayList<>();
        childCategories.add(childCategory);
        childCategory.setParentCategory(this);
    }
}
