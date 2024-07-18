package com.vismijatech.main.service;

import com.vismijatech.main.dto.SupplierDTO;
import com.vismijatech.main.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    Optional<SupplierDTO> saveSupplier(SupplierDTO supplierDTO);
    Optional<List<SupplierDTO>> getAllSuppliers();
    Optional<SupplierDTO> deleteSupplierById(Long id);
    Optional<SupplierDTO> updateSupplier(SupplierDTO supplierDTO);
    Optional<SupplierDTO> updateContactNumber(SupplierDTO supplierDTO);
    Optional<Supplier> findSupplierByName(String name);
}
