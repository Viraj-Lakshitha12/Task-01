package lk.ijse.gdse.taskbackend.service;

import lk.ijse.gdse.taskbackend.dto.UnitDTO;
import lk.ijse.gdse.taskbackend.entity.Unit;

import java.util.List;
import java.util.Optional;

public interface UnitService {
    Unit saveUnit(UnitDTO unitDTO);

    List<Unit> getAllUnit();

    void deleteUnit(String id);

    Optional<Unit> findUnitById(String id);

    Unit updateUnit(UnitDTO unitDTO);

    List<String> getAllUnitNames();
}
