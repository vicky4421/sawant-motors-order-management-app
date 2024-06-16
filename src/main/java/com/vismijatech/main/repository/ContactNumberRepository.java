package com.vismijatech.main.repository;

import com.vismijatech.main.entity.ContactNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactNumberRepository extends JpaRepository<ContactNumber, Long> {
}
