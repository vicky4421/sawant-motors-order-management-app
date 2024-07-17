package com.vismijatech.main.service;

import com.vismijatech.main.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    public Optional<Order> saveOrder(Order order);
    public Optional<List<Order>> getAllOrders();
    public Optional<Order> findOrderById(Long id);
    public Optional<Order> updateOrder(Order order);
    public Optional<Order> deleteOrder(Long id);
}
