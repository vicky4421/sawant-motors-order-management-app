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

        // check suppliers whatsapp number in alternate no list
        if (alternateNumbers.contains(supplier.getWhatsappNumber())) {
            return Optional.empty();
        }

        // check suppliers alternate number in both list
        if (supplier.getAlternateNumber() != null){
            if (whatsappNumbers.contains(supplier.getAlternateNumber()) || alternateNumbers.contains(supplier.getWhatsappNumber())) {
                return Optional.empty();
            }
        }

        // save supplier to database
        return Optional.of(supplierRepository.save(supplier));
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

    // update supplier
    @Override
    public Optional<Supplier> updateSupplier(Supplier supplier) {

        // get supplier from database
        Optional<Supplier> oldSupplier = supplierRepository.findById(supplier.getId());

        if (oldSupplier.isPresent()) {

            // assign suppliers value to old supplier if present
            if (!supplier.getName().isEmpty()) oldSupplier.get().setName(supplier.getName());
            if (!supplier.getWhatsappNumber().isEmpty()) oldSupplier.get().setWhatsappNumber(supplier.getWhatsappNumber());
            if (!supplier.getAlternateNumber().isEmpty()) oldSupplier.get().setAlternateNumber(supplier.getAlternateNumber());

            return saveSupplier(oldSupplier.get());
        }
        return Optional.empty();
    }

    // update suppliers contact number.
    @Override
    public Optional<Supplier> updateContactNumber(Supplier supplier) {

        // get supplier from database
        Optional<Supplier> oldSupplier = supplierRepository.findById(supplier.getId());
        if (oldSupplier.isPresent()){
            oldSupplier.get().setWhatsappNumber(supplier.getWhatsappNumber());
            oldSupplier.get().setAlternateNumber(supplier.getAlternateNumber());
            return saveSupplier(oldSupplier.get());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Supplier> findSupplierByName(String name) {
        Supplier supplier = supplierRepository.findByName(name);
        return Optional.ofNullable(supplier);
    }


}
