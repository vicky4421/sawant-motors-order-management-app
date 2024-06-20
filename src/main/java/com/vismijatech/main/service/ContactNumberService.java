package com.vismijatech.main.service;

import com.vismijatech.main.entity.ContactNumber;

import java.util.Optional;

public interface ContactNumberService {

    Optional<ContactNumber> saveContactNumber(ContactNumber contactNumber);
}
