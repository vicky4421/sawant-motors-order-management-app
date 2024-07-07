package com.vismijatech.main.repository;

import com.vismijatech.main.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Supplier findByName(String name);
}