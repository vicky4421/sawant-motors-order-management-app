package com.vismijatech.main.service;

import com.vismijatech.main.dto.SupplierDTO;
import com.vismijatech.main.entity.Supplier;
import com.vismijatech.main.repository.SupplierRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
    // get supplier repository reference
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    // constructor
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    // this method returns supplier if supplier's contact numbers are not in the whatsapp list and alternate number list.
    private Optional<Supplier> validateSupplier(Supplier supplier){
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
            if (whatsappNumbers.contains(supplier.getAlternateNumber()) || alternateNumbers.contains(supplier.getAlternateNumber())) {
                return Optional.empty();
            }
        }
        return Optional.of(supplier);
    }

    // save supplier
    @Override
    public Optional<SupplierDTO> saveSupplier(SupplierDTO supplierDTO) {

        // map dto to supplier
        Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);

        Optional<Supplier> validatedSupplier = validateSupplier(supplier);

        // if validated supplier is empty return empty
        if (validatedSupplier.isEmpty()) return Optional.empty();

        // save supplier to db
        Supplier savedSupplier = supplierRepository.save(supplier);

        // map to dto
        SupplierDTO savedDTO = modelMapper.map(savedSupplier, SupplierDTO.class);

        // return dto
        return Optional.of(savedDTO);
    }

    // get all suppliers
    @Override
    public Optional<List<SupplierDTO>> getAllSuppliers() {
        // get all suppliers from database
        List<Supplier> suppliers = supplierRepository.findAll();
        List<SupplierDTO> supplierDTOS = suppliers.stream().map(s -> modelMapper.map(s, SupplierDTO.class)).toList();
        if (!suppliers.isEmpty()) {
            return Optional.of(supplierDTOS);
        }
        return Optional.empty();
    }

    // delete supplier
    @Override
    public Optional<SupplierDTO> deleteSupplierById(Long id) {
        // get supplier from database
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            supplierRepository.deleteById(id);
            return Optional.of(modelMapper.map(supplier.get(), SupplierDTO.class));
        }
        return Optional.empty();
    }

    // update supplier
    @Override
    public Optional<SupplierDTO> updateSupplier(SupplierDTO supplierDTO) {

        // map dto to supplier
        Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);

        // get supplier from database
        Optional<Supplier> oldSupplier = supplierRepository.findById(supplier.getId());


        if (oldSupplier.isPresent()) {

            // assign new value to old supplier if present or old values will be persisted
            if (!supplier.getName().isEmpty()) oldSupplier.get().setName(supplier.getName());
            if (!supplier.getWhatsappNumber().isEmpty()) oldSupplier.get().setWhatsappNumber(supplier.getWhatsappNumber());
            if (!supplier.getAlternateNumber().isEmpty()) oldSupplier.get().setAlternateNumber(supplier.getAlternateNumber());

            // validate supplier
            Optional<Supplier> validatedSupplier = validateSupplier(oldSupplier.get());

            // return if validated supplier is empty
            if (validatedSupplier.isEmpty()) return Optional.empty();

            // else save supplier
            Supplier savedSupplier = supplierRepository.save(validatedSupplier.get());

            // map supplier to dto and save
            SupplierDTO map = modelMapper.map(savedSupplier, SupplierDTO.class);
            return Optional.of(map);
        }
        return Optional.empty();
    }

    // update suppliers contact number.
    @Override
    public Optional<SupplierDTO> updateContactNumber(SupplierDTO supplierDTO) {

        // map dto to supplier
        Supplier supplier = modelMapper.map(supplierDTO, Supplier.class);

        // get supplier from database
        Optional<Supplier> oldSupplier = supplierRepository.findById(supplier.getId());

        if (oldSupplier.isPresent()){
            oldSupplier.get().setWhatsappNumber(supplier.getWhatsappNumber());
            oldSupplier.get().setAlternateNumber(supplier.getAlternateNumber());

            // validate supplier
            Optional<Supplier> validatedSupplier = validateSupplier(oldSupplier.get());

            // return if validated supplier is empty
            if (validatedSupplier.isEmpty()) return Optional.empty();

            // else save supplier
            Supplier savedSupplier = supplierRepository.save(validatedSupplier.get());

            // map supplier to dto and save
            SupplierDTO map = modelMapper.map(savedSupplier, SupplierDTO.class);
            return Optional.of(map);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Supplier> findSupplierByName(String name) {
        Supplier supplier = supplierRepository.findByName(name);
        return Optional.ofNullable(supplier);
    }


}
