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
public class PartyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int partyId;
    private String partyName;
    @OneToMany(mappedBy = "partyEntity")        // mapped by: to avoid creation of new table
    private List<OrderEntity> orderList;

}
