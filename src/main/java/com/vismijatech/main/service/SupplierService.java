package com.vismijatech.main.service;

import com.vismijatech.main.entity.Supplier;

import java.util.Optional;

public interface SupplierService {
    Optional<Supplier> saveSupplier(Supplier supplier);
}
