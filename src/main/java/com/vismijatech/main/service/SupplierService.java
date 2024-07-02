package com.vismijatech.main.service;

import com.vismijatech.main.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    Optional<Supplier> saveSupplier(Supplier supplier);
    Optional<List<Supplier>> getAllSuppliers();
    Optional<Supplier> deleteSupplierById(Long id);
    Optional<Supplier> updateSupplier(Supplier supplier);
    Optional<Supplier> updateContactNumber(Supplier supplier);
}
