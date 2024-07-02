package com.vismijatech.main.service;

import com.vismijatech.main.entity.Supplier;
import com.vismijatech.main.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
    // get supplier repository reference
    private final SupplierRepository supplierRepository;

    // constructor
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    // save supplier
    @Override
    public Optional<Supplier> saveSupplier(Supplier supplier) {
        // get all suppliers
        List<Supplier> allSuppliers = supplierRepository.findAll();

        // get all suppliers whatsapp number
        List<String> whatsappNumbers = allSuppliers.stream().map(Supplier::getWhatsappNumber).toList();

        // get all suppliers alternate number
        List<String> alternateNumbers = allSuppliers.stream().map(Supplier::getAlternateNumber).toList();

        // check suppliers whatsapp number in both list
        if (whatsappNumbers.contains(supplier.getWhatsappNumber()) || alternateNumbers.contains(supplier.getWhatsappNumber())) {
            return Optional.empty();
        }

        // check suppliers alternate number in both list
        if (supplier.getAlternateNumber() != null){
            if (whatsappNumbers.contains(supplier.getAlternateNumber()) || alternateNumbers.contains(supplier.getAlternateNumber())) {
                return Optional.empty();
            }
        }

        // save supplier to database
        Supplier supplier1 = supplierRepository.save(supplier);
        return Optional.of(supplier1);
    }

    // get all suppliers
    @Override
    public Optional<List<Supplier>> getAllSuppliers() {
        // get all suppliers from database
        List<Supplier> suppliers = supplierRepository.findAll();
        if (!suppliers.isEmpty()) {
            return Optional.of(suppliers);
        }
        return Optional.empty();
    }

    // delete supplier
    @Override
    public Optional<Supplier> deleteSupplierById(Long id) {
        // get supplier from database
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            supplierRepository.deleteById(id);
            return supplier;
        }
        return Optional.empty();
    }


    @Override
    public Optional<Supplier> updateSupplier(Supplier supplier) {
        // get supplier from database
        Optional<Supplier> oldSupplier = supplierRepository.findById(supplier.getId());
        if (oldSupplier.isPresent()) {
            Supplier newSupplier = oldSupplier.get();
            if (!supplier.getName().isEmpty()) {
                newSupplier.setName(supplier.getName());
            }
            if (!supplier.getAlternateNumber().isEmpty()) {
                newSupplier.setAlternateNumber(supplier.getAlternateNumber());
            }
            if (!supplier.getWhatsappNumber().isEmpty()) {
                newSupplier.setWhatsappNumber(supplier.getWhatsappNumber());
            }

            return Optional.of(supplierRepository.save(newSupplier));
        }
        return Optional.empty();
    }

    @Override
    public Optional<Supplier> updateContactNumber(Supplier supplier) {
        // get supplier from database
        Optional<Supplier> oldSupplier = supplierRepository.findById(supplier.getId());
        if (oldSupplier.isPresent()){
            if (supplier.getWhatsappNumber() == null) oldSupplier.get().setWhatsappNumber(null);
            if (supplier.getAlternateNumber() == null) oldSupplier.get().setAlternateNumber(null);
            return Optional.of(supplierRepository.save(oldSupplier.get()));
        }
        return Optional.empty();
    }


}
