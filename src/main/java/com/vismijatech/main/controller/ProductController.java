package com.vismijatech.main.controller;

import com.vismijatech.main.dto.ProductDTO;
import com.vismijatech.main.entity.Product;
import com.vismijatech.main.entity.ProductCategory;
import com.vismijatech.main.entity.Unit;
import com.vismijatech.main.service.ProductCategoryService;
import com.vismijatech.main.service.ProductService;
import com.vismijatech.main.service.UnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    // get product service reference
    private final ProductService productService;
    private final UnitService unitService;
    private final ProductCategoryService categoryService; 
    // constructor
    public ProductController(ProductService productService, UnitService unitService, ProductCategoryService categoryService) {
        this.productService = productService;
        this.unitService = unitService;
        this.categoryService = categoryService;
    }

    // save product
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.getName())
                .partNumber(productDTO.getPartNumber())
                .build();


        Optional<Unit> unit = unitService.findUnitByName(productDTO.getUnit());
        if (unit.isPresent()) {
            product.setUnit(unit.get());
        } else {
            return new ResponseEntity("Unit not found!!", HttpStatus.BAD_REQUEST);
        }

        Optional<ProductCategory> category = categoryService.findCategoryByName(productDTO.getCategory());
        if (category.isPresent()) {
            product.setCategory(category.get());
        } else {
            return new ResponseEntity("Category not found!", HttpStatus.BAD_REQUEST);
        }

        return productService.addProduct(product)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Product not added!", HttpStatus.BAD_REQUEST));
    }

    // update product
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO) {

        System.out.println("productDTO" + productDTO);

        Optional<Product> productToUpdate = productService.getProductById(productDTO.getId());
        if (productToUpdate.isEmpty()) {
            return new ResponseEntity("Product not found!", HttpStatus.BAD_REQUEST);
        }

        Product product = productToUpdate.get();

        // update product id if provided
        if (productDTO.getId() != null) {
            product.setId(productDTO.getId());
        }

        // update product name if provided
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }

        // update product part number if provided
        if (productDTO.getPartNumber() != null) {
            product.setPartNumber(productDTO.getPartNumber());
        }

        // update product category if provided
        if (productDTO.getCategory() != null) {
            Optional<ProductCategory> category = categoryService.findCategoryByName(productDTO.getCategory());
            category.ifPresent(product::setCategory);
        }

        // update product unit if provided
        if (productDTO.getUnit() != null) {
            Optional<Unit> unit = unitService.findUnitByName(productDTO.getUnit());
            unit.ifPresent(product::setUnit);
        }

        return productService.updateProduct(product)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Product not updated!", HttpStatus.BAD_REQUEST));
    }

    // delete product
    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {

        Optional<Product> productToDelete = productService.getProductById(id);
        if (productToDelete.isEmpty()) {
            return new ResponseEntity("Product not found!", HttpStatus.BAD_REQUEST);
        }

        Product product = productToDelete.get();


        return productService.deleteProduct(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Product not found!", HttpStatus.BAD_REQUEST));
    }

    // get all products
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/allProducts")
    public ResponseEntity<?> getAllProducts() {
        return productService.getAllProducts()
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Products not found!", HttpStatus.BAD_REQUEST));
    }
}
