package com.vismijatech.main.service;

import com.vismijatech.main.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public Optional<Product> addProduct(Product product);
    public Optional<Product> updateProduct(Product product);
    public Optional<Product> deleteProduct(Long id);
    public Optional<List<Product>> getAllProducts();
    public Optional<Product> getProductById(Long id);
}
