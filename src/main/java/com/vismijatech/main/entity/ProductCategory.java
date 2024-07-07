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
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String categoryName;

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
    @JsonIgnore
    private List<Product> productList;

    // Self-referencing relationship for parent category
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ProductCategory parentCategory;

    // Self-referencing relationship for child categories
    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.EAGER)
    private List<ProductCategory> childCategories;

    // constructors
    public ProductCategory(String name) {
        this.categoryName = name;
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
