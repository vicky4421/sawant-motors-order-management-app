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
            System.out.println("first block");
            return Optional.empty();
        }

        // check suppliers alternate number in both list
        if (supplier.getAlternateNumber() != null){
            if (whatsappNumbers.contains(supplier.getAlternateNumber()) || alternateNumbers.contains(supplier.getAlternateNumber())) {
                System.out.println("second block");
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


}
