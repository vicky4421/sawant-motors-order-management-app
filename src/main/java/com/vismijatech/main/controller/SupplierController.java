package com.vismijatech.main.controller;

import com.vismijatech.main.dto.SupplierDTO;
import com.vismijatech.main.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        Optional<SupplierDTO> optionalSupplierDTO = supplierService.saveSupplier(supplierDTO);
        return optionalSupplierDTO.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity("Supplier not saved!", HttpStatus.BAD_REQUEST));
    }

    // get all suppliers
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/allSuppliers")
    public ResponseEntity<?> getAllSuppliers() {
        Optional<List<SupplierDTO>> supplierDTOS = supplierService.getAllSuppliers();
        return supplierDTOS.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity("Suppliers not found", HttpStatus.BAD_REQUEST));
    }

    // delete supplier
    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/deleteSupplier/{id}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Long id) {
        Optional<SupplierDTO> supplier = supplierService.deleteSupplierById(id);
        return supplier.map(ResponseEntity::ok).orElseGet(()-> new ResponseEntity("Supplier not found", HttpStatus.BAD_REQUEST));
    }

    // update supplier
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping
    public ResponseEntity<?> updateSupplier(@RequestBody SupplierDTO supplierDTO) {

        // update supplier
        Optional<SupplierDTO> updatedSupplier = supplierService.updateSupplier(supplierDTO);

        return updatedSupplier.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity("Supplier not updated", HttpStatus.BAD_REQUEST));
    }

    // update contact
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/updateContact")
    public ResponseEntity<?> updateContact(@RequestBody SupplierDTO supplierDTO){

        // update contact no.
        Optional<SupplierDTO> updatedSupplier = supplierService.updateContactNumber(supplierDTO);

        return updatedSupplier.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity("Contact not updated", HttpStatus.BAD_REQUEST));
    }
}
