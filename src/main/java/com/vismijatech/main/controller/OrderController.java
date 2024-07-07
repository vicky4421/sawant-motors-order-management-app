package com.vismijatech.main.controller;

import com.vismijatech.main.dto.OrderDTO;
import com.vismijatech.main.entity.Order;
import com.vismijatech.main.entity.Supplier;
import com.vismijatech.main.service.OrderService;
import com.vismijatech.main.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    // get order service reference
    private final OrderService orderService;
    private final SupplierService supplierService;

    // constructor
    public OrderController(OrderService orderService, SupplierService supplierService) {
        this.orderService = orderService;
        this.supplierService = supplierService;
    }

    // save order
    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody OrderDTO orderDTO){
        Order newOrder = Order.builder()
                .name(orderDTO.getName())
                .build();

        if (orderDTO.getSupplier() != null){
            Optional<Supplier> supplier = supplierService.findSupplierByName(orderDTO.getSupplier());
            supplier.ifPresent(newOrder::setSupplier);
        } else {
            return new ResponseEntity("Supplier not found!", HttpStatus.BAD_REQUEST);
        }

        LocalDateTime localDate = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");
        String formattedDate = formatter.format(localDate);
        System.out.println("formattedDate" + formattedDate);
        newOrder.setOrderDate(formattedDate);

        return orderService.saveOrder(newOrder)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Order not saved!", HttpStatus.BAD_REQUEST));
    }
}
