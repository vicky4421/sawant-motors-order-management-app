package com.vismijatech.main.service;

import com.vismijatech.main.entity.ProductCategory;
import com.vismijatech.main.repository.ProductCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    // get repository reference
    private final ProductCategoryRepository categoryRepository;

    // constructor
    public ProductCategoryServiceImpl(ProductCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    // find category by name
    @Override
    public Optional<ProductCategory> findCategoryByName(String name) {
        return Optional.of(categoryRepository.findByCategoryName(name));
    }
    // add category
    @Override
    public Optional<ProductCategory> addCategory(ProductCategory category) {
        return Optional.of(categoryRepository.save(category));
    }
    // get all categories
    @Override
    public Optional<List<ProductCategory>> getAllCategories() {
        return Optional.of(categoryRepository.findAll());
    }

    @Override
    public Optional<ProductCategory> findCategoryById(Long id) {
        // get category from database
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<ProductCategory> deleteCategory(Long id) {
        // find category in database
        Optional<ProductCategory> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            categoryRepository.deleteById(id);
            return category;
        }
        return Optional.empty();
    }
}
