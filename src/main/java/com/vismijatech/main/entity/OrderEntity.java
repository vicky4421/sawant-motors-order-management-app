package com.vismijatech.main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderNo;

    private Date orderDate;
    @ManyToOne
    @JoinColumn(name = "partyId")       // foreign key name in order entity table.
    private PartyEntity partyEntity;

}
