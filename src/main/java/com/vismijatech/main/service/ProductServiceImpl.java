package com.vismijatech.main.service;

import com.vismijatech.main.entity.Product;
import com.vismijatech.main.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    // get repository reference
    private final ProductRepository productRepository;

    // constructor
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    // add product
    @Override
    public Optional<Product> addProduct(Product product) {
        Product product1 = productRepository.save(product);
        return Optional.of(product1);
    }

    // update product
    @Override
    public Optional<Product> updateProduct(Product product) {
        Optional<Product> product1 = productRepository.findById(product.getId());
        if (product1.isPresent()) {
            Product product2 = productRepository.save(product);
            return Optional.ofNullable(Optional.of(product2).orElseThrow(() -> new RuntimeException("Product not updated!")));
        }
        throw new RuntimeException("Product not found!");
    }

    // delete product
    @Override
    public Optional<Product> deleteProduct(Long id) {
        Optional<Product> product1 = productRepository.findById(id);
        if (product1.isPresent()) {
            productRepository.delete(product1.get());
            return product1;
        }
        return Optional.empty();
    }

    // get all products
    @Override
    public Optional<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (!products.isEmpty()) {
            return Optional.of(products);
        }
        return Optional.empty();
    }

    // get product by id
    @Override
    public Optional<Product> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return Optional.ofNullable(product.orElseThrow(() -> new RuntimeException("Product not found!")));
    }
}
