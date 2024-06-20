package com.vismijatech.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    // bidirectional relation between contact number and party
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplier_id")
    @JsonIgnore
    private Supplier supplier;

    // Constructors
    public ContactNumber(String number) {
        this.phoneNumber = number;
    }
}
