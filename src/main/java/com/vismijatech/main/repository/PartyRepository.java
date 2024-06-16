package com.vismijatech.main.repository;

import com.vismijatech.main.entity.Party;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartyRepository extends JpaRepository<Party, Long> {
}
