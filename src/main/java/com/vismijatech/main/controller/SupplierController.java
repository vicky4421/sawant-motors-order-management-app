package com.vismijatech.main.controller;

import com.vismijatech.main.dto.SupplierDTO;
import com.vismijatech.main.entity.Supplier;
import com.vismijatech.main.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/supplier")
@CrossOrigin(origins = "http://localhost:5173")
public class SupplierController {
    // get supplier service and contact number service reference
    private final SupplierService supplierService;

    private final ModelMapper modelMapper;

    // constructor
    public SupplierController(SupplierService supplierService, ModelMapper modelMapper) {
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity<?> saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier(supplierDTO.getName(), supplierDTO.getWhatsappNumber());

        // check if alternate number is not empty
        if (supplierDTO.getAlternateNumber() != null) supplier.setAlternateNumber(supplierDTO.getAlternateNumber());

        return supplierService.saveSupplier(supplier)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Supplier not saved!", HttpStatus.BAD_REQUEST));
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/allSuppliers")
    public ResponseEntity<?> getAllSuppliers() {
        // get all suppliers from database
        Optional<List<Supplier>> suppliers = supplierService.getAllSuppliers();
        List<SupplierDTO> supplierDTOList = new ArrayList<>();
        if (suppliers.isPresent()){
            List<Supplier> supplierList = suppliers.get();
            supplierList.forEach(supplier -> {
                SupplierDTO supplierDTO = modelMapper.map(supplier, SupplierDTO.class);
                supplierDTOList.add(supplierDTO);
            });
        }

        Optional<List<SupplierDTO>> suppliersDTO = Optional.of(supplierDTOList);

        return suppliersDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("No suppliers found!", HttpStatus.NOT_FOUND));

    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {
        Supplier supplier = supplierService.deleteSupplierById(id).get();

        SupplierDTO supplierDTO = modelMapper.map(supplier, SupplierDTO.class);

        Optional<SupplierDTO> optionalSupplierDTO = Optional.of(supplierDTO);

        return optionalSupplierDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Supplier not found!", HttpStatus.NOT_FOUND));
    }
}
