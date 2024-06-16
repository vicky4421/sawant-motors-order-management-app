package com.vismijatech.main.repository;

import com.vismijatech.main.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
