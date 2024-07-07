package com.vismijatech.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @OneToOne(
            mappedBy = "product",
            cascade = CascadeType.ALL
    )
    private OrderDetails orderDetails;

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

    // convenience methods to add unit
    public void addUnit(Unit unit) {
        this.unit = unit;
    }
}
