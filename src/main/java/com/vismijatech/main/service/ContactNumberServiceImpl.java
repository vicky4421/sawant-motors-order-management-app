package com.vismijatech.main.service;

import com.vismijatech.main.entity.ContactNumber;
import com.vismijatech.main.repository.ContactNumberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContactNumberServiceImpl implements ContactNumberService {
    // get contact number repository reference
    private final ContactNumberRepository contactNumberRepository;

    // constructor
    public ContactNumberServiceImpl(ContactNumberRepository contactNumberRepository) {
        this.contactNumberRepository = contactNumberRepository;
    }

    @Override
    public Optional<ContactNumber> saveContactNumber(ContactNumber contactNumber) {
        // save contact number to database
        ContactNumber contactNo = contactNumberRepository.save(contactNumber);
        return Optional.of(contactNo);
    }
}
