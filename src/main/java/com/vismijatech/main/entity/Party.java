package com.vismijatech.main.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // bidirectional relation between party and contact number
    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL)
    private List<ContactNumber> contactNumbersList;

    // Constructors
    public Party(String name) {
        this.name = name;
    }

    public Party(String name, List<ContactNumber> contactNumbersList) {
        this.name = name;
        this.contactNumbersList = contactNumbersList;
    }

    // convenience method for bidirectional relation between party and contact number
    public void addContactNumber(ContactNumber contactNumber) {
        if (contactNumbersList == null) contactNumbersList = new ArrayList<>();
        contactNumbersList.add(contactNumber);
        contactNumber.setParty(this);
    }
}
