package com.vismijatech.main.controller;

import com.vismijatech.main.dto.ProductCategoryDTO;
import com.vismijatech.main.entity.ProductCategory;
import com.vismijatech.main.service.ProductCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {
    // get product category service reference
    private final ProductCategoryService categoryService;
    private final ModelMapper modelMapper;
    // constructor
    public ProductCategoryController(ProductCategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    // add category
    @PostMapping()
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> addCategory(@RequestBody ProductCategoryDTO categoryDTO) {
        // create category from dto
        ProductCategory category = ProductCategory.builder()
                .categoryName(categoryDTO.getCategoryName())
                .build();

        System.out.println("category" + category);


        // set parent category if not null
        if (categoryDTO.getParentCategory() != null) {
            Optional<ProductCategory> parentCategory = categoryService.findCategoryByName(categoryDTO.getParentCategory());
            parentCategory.ifPresent(category::setParentCategory);
        }

        return categoryService.addCategory(category)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Category not saved!", HttpStatus.BAD_REQUEST));
    }

    // get all categories
    @GetMapping("/allCategories")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> getAllCategories() {
        // get all categories from database
        Optional<List<ProductCategory>> categories = categoryService.getAllCategories();
        List<ProductCategoryDTO> categoryDTOList = new ArrayList<>();
        if (categories.isPresent()){
            List<ProductCategory> categoryList = categories.get();
            categoryList.forEach(category -> {
                ProductCategoryDTO categoryDTO = ProductCategoryDTO.builder()
                        .id(category.getId())
                        .categoryName(category.getCategoryName())
                        .parentCategory(category.getParentCategory() != null ? category.getParentCategory().getCategoryName() : null)
                        .build();
                categoryDTOList.add(categoryDTO);
            });
        }

        Optional<List<ProductCategoryDTO>> categoriesDTO = Optional.of(categoryDTOList);
        return categoriesDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Categories not found!", HttpStatus.NOT_FOUND));
    }

    // delete category
    @DeleteMapping("/deleteCategory/{id}")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
        Optional<ProductCategory> category = categoryService.deleteCategory(id);
        ProductCategoryDTO categoryDTO = null;
        if (category.isPresent()){
            categoryDTO = ProductCategoryDTO.builder()
                    .id(category.get().getId())
                    .categoryName(category.get().getCategoryName())
                    .build();
            Optional<ProductCategoryDTO> optionalCategoryDTO = Optional.ofNullable(categoryDTO);
            return optionalCategoryDTO
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity("Category not found!", HttpStatus.NOT_FOUND));
        }
        return new ResponseEntity("Category not found!", HttpStatus.NOT_FOUND);
    }

    // update category
    @PutMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> updateCategory(@RequestBody ProductCategoryDTO categoryDTO) {
        ProductCategory category = ProductCategory.builder()
                .id(categoryDTO.getId())
                .categoryName(categoryDTO.getCategoryName())
                .build();

        if (categoryDTO.getParentCategory() != null) {
            Optional<ProductCategory> parentCategory = categoryService.findCategoryByName(categoryDTO.getParentCategory());
            parentCategory.ifPresent(category::setParentCategory);
        }

        return categoryService.updateCategory(category)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Category not updated!", HttpStatus.BAD_REQUEST));
    }
}
