package com.vismijatech.main.service;

import com.vismijatech.main.entity.Supplier;
import com.vismijatech.main.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    // get supplier repository reference
    private final SupplierRepository supplierRepository;

    // constructor
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }
    @Override
    public Optional<Supplier> saveSupplier(Supplier supplier) {
        // save supplier to database
        Supplier supplier1 = supplierRepository.save(supplier);
        return Optional.of(supplier1);
    }
}
