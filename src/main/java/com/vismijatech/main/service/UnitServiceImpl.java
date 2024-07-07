package com.vismijatech.main.service;

import com.vismijatech.main.entity.Unit;
import com.vismijatech.main.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitServiceImpl implements UnitService {
    // get repository reference
    private final UnitRepository unitRepository;
    // constructor

    public UnitServiceImpl(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    // save unit
    @Override
    public Optional<Unit> addUnit(Unit unit) {
        // save unit to database
        Unit unit1 = unitRepository.save(unit);
        return Optional.of(unit1);
    }

    // update unit
    @Override
    public Optional<Unit> updateUnit(Unit unit) {
        // find unit in database
        Optional<Unit> unit1 = unitRepository.findById(unit.getId());
        if (unit1.isPresent()) {
            // update unit
            Unit unit2 = unitRepository.save(unit);
            return Optional.of(unit2);
        }
        return Optional.empty();
    }

    // get all units
    @Override
    public Optional<List<Unit>> getAllUnits() {
        List<Unit> units = unitRepository.findAll();
        if (!units.isEmpty()) {
            return Optional.of(units);
        }
        return Optional.empty();
    }

    // delete unit
    @Override
    public Optional<Unit> deleteUnit(Long id) {
        // find unit in database
        Optional<Unit> unit1 = unitRepository.findById(id);
        if (unit1.isPresent()) {
            // delete unit
            unitRepository.deleteById(id);
            return unit1;
        }
        return Optional.empty();
    }

    // find unit by name
    @Override
    public Optional<Unit> findUnitByName(String name) {
        Unit unit = unitRepository.findByName(name);
        if (unit != null) return Optional.ofNullable(Optional.of(unit).orElseThrow(() -> new RuntimeException("Unit not found!")));
        else return Optional.empty();
    }

    @Override
    public Optional<Unit> findUnitById(Long id) {
       return unitRepository.findById(id);
    }

}
