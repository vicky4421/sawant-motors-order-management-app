package com.vismijatech.main.service;

import com.vismijatech.main.entity.ProductCategory;

import java.util.List;
import java.util.Optional;

public interface ProductCategoryService {
    Optional<ProductCategory> findCategoryByName(String name);
    Optional<ProductCategory> addCategory(ProductCategory category);
    Optional<List<ProductCategory>> getAllCategories();
    Optional<ProductCategory> findCategoryById(Long id);
    Optional<ProductCategory> deleteCategory(Long id);
}
