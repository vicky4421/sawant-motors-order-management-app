package com.vismijatech.main.controller;

import com.vismijatech.main.dto.OrderDTO;
import com.vismijatech.main.entity.Order;
import com.vismijatech.main.entity.Supplier;
import com.vismijatech.main.service.OrderService;
import com.vismijatech.main.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    // get order service reference
    private final OrderService orderService;
    private final SupplierService supplierService;
    private final ModelMapper modelMapper;

    // constructor
    public OrderController(OrderService orderService, SupplierService supplierService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.supplierService = supplierService;
        this.modelMapper = modelMapper;
    }

    // save order
    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody OrderDTO orderDTO){
        Order newOrder = Order.builder()
                .name(orderDTO.getName())
                .build();

        if (orderDTO.getSupplierName() != null){
            Optional<Supplier> supplier = supplierService.findSupplierByName(orderDTO.getSupplierName());
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

    // get all orders
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/allOrders")
    public ResponseEntity<?> getAllOrders(){
        Optional<List<Order>> orders = orderService.getAllOrders();
        if (orders.isPresent()){
            List<Order> orderList = orders.get();
            List<OrderDTO> orderDTOList = orderList.stream()
                    .map(
                            order -> {
                                return modelMapper.map(order, OrderDTO.class);
                            }
                    )
                    .collect(Collectors.toList());
            Optional<List<OrderDTO>> orderDTOS = Optional.of(orderDTOList);
            return orderDTOS
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity("No orders found", HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>("No orders found", HttpStatus.NOT_FOUND);
        }
    }
}
