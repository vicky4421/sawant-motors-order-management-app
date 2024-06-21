package com.vismijatech.main.controller;

import com.vismijatech.main.dto.SupplierDTO;
import com.vismijatech.main.entity.ContactNumber;
import com.vismijatech.main.entity.Supplier;
import com.vismijatech.main.service.ContactNumberService;
import com.vismijatech.main.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/supplier")
@CrossOrigin(origins = "http://localhost:5173")
public class SupplierController {
    // get supplier service and contact number service reference
    private final SupplierService supplierService;
    private final ContactNumberService contactNumberService;

    private final ModelMapper modelMapper;

    // constructor
    public SupplierController(SupplierService supplierService, ContactNumberService contactNumberService, ModelMapper modelMapper) {
        this.supplierService = supplierService;
        this.contactNumberService = contactNumberService;
        this.modelMapper = modelMapper;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity<?> saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier(supplierDTO.getName());

        // check if alternate number is not empty
        if (supplierDTO.getAlternateNumber() != null) supplier.addContactNumber(
                new ContactNumber(supplierDTO.getAlternateNumber())
        );

        supplier.addContactNumber(new ContactNumber(supplierDTO.getWhatsappNumber()));

        return supplierService.saveSupplier(supplier)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Supplier not saved!", HttpStatus.BAD_REQUEST));
    }
}
