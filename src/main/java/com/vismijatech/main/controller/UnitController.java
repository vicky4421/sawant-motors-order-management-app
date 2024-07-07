package com.vismijatech.main.controller;

import com.vismijatech.main.dto.UnitDTO;
import com.vismijatech.main.entity.Unit;
import com.vismijatech.main.service.UnitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/unit")
public class UnitController {
    // get unit service reference
    private final UnitService unitService;
    // constructor
    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    // add unit
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping
    public ResponseEntity<?> addUnit(@RequestBody UnitDTO unitDTO) {
        Unit unit = Unit.builder()
                        .name(unitDTO.getName())
                        .shortName(unitDTO.getShortName())
                        .build();
        return unitService.addUnit(unit)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Unit not added!", HttpStatus.BAD_REQUEST));
    }

    // update unit
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping
    public ResponseEntity<?> updateUnit(@RequestBody UnitDTO unitDTO) {
        Unit unit = Unit.builder()
                        .id(unitDTO.getId())
                        .name(unitDTO.getName())
                        .shortName(unitDTO.getShortName())
                        .build();
        return unitService.updateUnit(unit)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("Unit not updated!", HttpStatus.BAD_REQUEST));
    }

    // get all units
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/allUnits")
    public ResponseEntity<?> getAllUnits() {
        Optional<List<Unit>> units = unitService.getAllUnits();
        List<UnitDTO> unitDTOList = new ArrayList<>();
        if (units.isPresent()){
            List<Unit> unitList = units.get();
            unitList.forEach(unit -> {
                UnitDTO unitDTO = UnitDTO.builder()
                        .id(unit.getId())
                        .name(unit.getName())
                        .shortName(unit.getShortName())
                        .build();
                unitDTOList.add(unitDTO);
            });
        }
        Optional<List<UnitDTO>> unitsDTO = Optional.of(unitDTOList);
        return unitsDTO
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity("No units found!", HttpStatus.NOT_FOUND));
    }

    // delete unit
    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/deleteUnit/{id}")
    public ResponseEntity<?> deleteUnit(@PathVariable Long id) {
        Optional<Unit> unit = unitService.deleteUnit(id);
        UnitDTO unitDTO = null;
        if (unit.isPresent()){
            unitDTO = UnitDTO.builder()
                    .id(unit.get().getId())
                    .name(unit.get().getName())
                    .shortName(unit.get().getShortName())
                    .build();
            Optional<UnitDTO> optionalUnitDTO = Optional.ofNullable(unitDTO);
            return optionalUnitDTO
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity("Unit not found!", HttpStatus.NOT_FOUND));
        }
        return new ResponseEntity("Unit not found!", HttpStatus.NOT_FOUND);
    }
}
