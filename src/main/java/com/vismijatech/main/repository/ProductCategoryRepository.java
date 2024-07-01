package com.vismijatech.main.repository;

import com.vismijatech.main.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    // find category by name
    ProductCategory findByCategoryName(String name);
}
