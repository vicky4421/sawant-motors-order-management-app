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
        ProductCategory category = categoryRepository.findByCategoryName(name);
        if (category != null) return Optional.ofNullable(Optional.of(category).orElseThrow(() -> new RuntimeException("Category not found")));
        else return Optional.empty();
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

    // find category by id
    @Override
    public Optional<ProductCategory> findCategoryById(Long id) {
        // get category from database
        return categoryRepository.findById(id);
    }

    // delete category
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

    // update category
    @Override
    public Optional<ProductCategory> updateCategory(ProductCategory category) {
        // find category in database
        Optional<ProductCategory> oldCategory = categoryRepository.findById(category.getId());

        if (oldCategory.isPresent()){
            ProductCategory newCategory = oldCategory.get();
            if (category.getCategoryName() != null) newCategory.setCategoryName(category.getCategoryName());
            if (category.getParentCategory() != null) newCategory.setParentCategory(category.getParentCategory());
            if (category.getParentCategory() == null) newCategory.setParentCategory(null);
            return Optional.of(categoryRepository.save(newCategory));
        }
        return Optional.empty();
    }
}
