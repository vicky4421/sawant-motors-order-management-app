package com.vismijatech.main.service;

import com.vismijatech.main.entity.Order;
import com.vismijatech.main.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    // get order repository reference
    private final OrderRepository orderRepository;

    // constructor
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public Optional<Order> saveOrder(Order order) {
        return Optional.of(orderRepository.save(order));
    }

    @Override
    public Optional<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return Optional.of(orders);
    }

    @Override
    public Optional<Order> findOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<Order> updateOrder(Order order) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> deleteOrder(Long id) {
        return Optional.empty();
    }
}
