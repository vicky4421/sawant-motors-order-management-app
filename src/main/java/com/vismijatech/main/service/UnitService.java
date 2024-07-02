package com.vismijatech.main.service;

import com.vismijatech.main.entity.Unit;

import java.util.List;
import java.util.Optional;

public interface UnitService {
    public Optional<Unit> addUnit(Unit unit);
    public Optional<Unit> updateUnit(Unit unit);
    public Optional<List<Unit>> getAllUnits();
    public Optional<Unit> deleteUnit(Long id);
}
