package com.vismijatech.main.repository;

import com.vismijatech.main.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
