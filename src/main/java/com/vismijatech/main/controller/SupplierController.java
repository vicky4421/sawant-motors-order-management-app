package com.vismijatech.main.controller;

import com.vismijatech.main.dto.SupplierDTO;
import com.vismijatech.main.entity.Supplier;
import com.vismijatech.main.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
    // get supplier service and contact number service reference
    private final SupplierService supplierService;

    private final ModelMapper modelMapper;

    // constructor
    public SupplierController(SupplierService supplierService, ModelMapper modelMapper) {
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
    }

    // save supplier
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity<?> saveSupplier(@RequestBody SupplierDTO supplierDTO) {

        // create supplier with non-optional fields i.e. name and whatsapp no.
        Supplier supplier = new Supplier(supplierDTO.getName(), supplierDTO.getWhatsappNumber());

        // if alternate no is present in dto then assign it to supplier.
        if (supplierDTO.getAlternateNumber() != null) supplier.setAlternateNumber(supplierDTO.getAlternateNumber());

        // save supplier
        Optional<Supplier> supplierToSave = supplierService.saveSupplier(supplier);

        // if supplier is not empty then map it to supplier dto
        if (supplierToSave.isPresent()) {
            return Optional.of(modelMapper.map(supplierToSave, SupplierDTO.class))
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity("Supplier saved!", HttpStatus.BAD_REQUEST));
        }

        return new ResponseEntity("Supplier not saved!", HttpStatus.BAD_REQUEST);
    }

    // get all suppliers
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/allSuppliers")
    public ResponseEntity<?> getAllSuppliers() {
        // get all suppliers from database
        Optional<List<Supplier>> suppliers = supplierService.getAllSuppliers();

        // if suppliers list is present then map it to supplier dto
        if (suppliers.isPresent()){
            List<SupplierDTO> supplierDTOS = suppliers.get().stream()
                    .map(
                            supplier -> {
                                return modelMapper.map(supplier, SupplierDTO.class);
                            }
                    )
                    .collect(Collectors.toList());
            return Optional.of(supplierDTOS)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity("No supplier found", HttpStatus.BAD_REQUEST));
        } else {
            return new ResponseEntity<>("No suppliers found", HttpStatus.NOT_FOUND);
        }
    }

    // delete supplier
    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/deleteSupplier/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {

        // delete supplier
        Optional<Supplier> supplier = supplierService.deleteSupplierById(id);

        // if supplier has value then map it in dto and response
        if (supplier.isPresent()){
            SupplierDTO supplierDTO = modelMapper.map(supplier, SupplierDTO.class);
            Optional<SupplierDTO> optionalSupplierDTO = Optional.of(supplierDTO);

            return optionalSupplierDTO
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity("Supplier not found!", HttpStatus.BAD_REQUEST));
        }

        return new ResponseEntity("Supplier not found!", HttpStatus.NOT_FOUND);
    }

    // update supplier
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping
    public ResponseEntity<?> updateSupplier(@RequestBody SupplierDTO supplierDTO) {
        Supplier supplier = Supplier.builder()
                .id(supplierDTO.getId())
                .build();

        // check if name is not empty
        if (supplierDTO.getName() != null) supplier.setName(supplierDTO.getName());

        // check if whatsapp number is not empty
        if (supplierDTO.getWhatsappNumber() != null) supplier.setWhatsappNumber(supplierDTO.getWhatsappNumber());

        // check if alternate number is not empty
        if (supplierDTO.getAlternateNumber() != null) supplier.setAlternateNumber(supplierDTO.getAlternateNumber());

        // update supplier
        Optional<Supplier> updatedSupplier = supplierService.updateSupplier(supplier);

        // if updated supplier has value then map it to dto and response.
        if (updatedSupplier.isPresent()){
            SupplierDTO updatedSupplierDTO = modelMapper.map(updatedSupplier.get(), SupplierDTO.class);
            return Optional.of(updatedSupplierDTO)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity("Supplier not found!", HttpStatus.BAD_REQUEST));
        } else return new ResponseEntity<>("Supplier not updated", HttpStatus.BAD_REQUEST);
    }

    // update contact
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/updateContact")
    public ResponseEntity<?> updateContact(@RequestBody SupplierDTO supplierDTO){
        // create supplier from dto
        Supplier supplier = Supplier.builder()
                .name(supplierDTO.getName())
                .id(supplierDTO.getId())
                .whatsappNumber(supplierDTO.getWhatsappNumber())
                .alternateNumber(supplierDTO.getAlternateNumber())
                .build();

        // update contact no.
        Optional<Supplier> updatedSupplier = supplierService.updateContactNumber(supplier);

        // updates supplier has value then map it to dto and response.
        if (updatedSupplier.isPresent()){
            SupplierDTO updatedSupplierDTO = modelMapper.map(updatedSupplier.get(), SupplierDTO.class);
            return Optional.of(updatedSupplierDTO)
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity("Supplier not found!", HttpStatus.BAD_REQUEST));
        } else return new ResponseEntity<>("Contact not updated", HttpStatus.BAD_REQUEST);
    }
}
