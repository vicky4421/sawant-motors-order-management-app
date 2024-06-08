package com.vismijatech.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class OrderDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderDetailId;
    @ManyToOne
    @JoinColumn(name = "orderId")
    private OrderEntity orderEntity;
    private List<ProductEntity> productList;
}
