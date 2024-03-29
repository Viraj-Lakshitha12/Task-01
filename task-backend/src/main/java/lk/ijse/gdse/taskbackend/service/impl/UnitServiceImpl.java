package lk.ijse.gdse.taskbackend.service.impl;

import lk.ijse.gdse.taskbackend.dto.UnitDTO;
import lk.ijse.gdse.taskbackend.entity.Unit;
import lk.ijse.gdse.taskbackend.repository.UnitRepo;
import lk.ijse.gdse.taskbackend.service.UnitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitServiceImpl implements UnitService {

    @Autowired
    private final UnitRepo unitRepo;

    @Autowired
    private final ModelMapper modelMapper;

    public UnitServiceImpl(UnitRepo unitRepo, ModelMapper modelMapper) {
        this.unitRepo = unitRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Unit saveUnit(UnitDTO unitDTO) {
        return unitRepo.save(modelMapper.map(unitDTO, Unit.class));
    }

    @Override
    public List<Unit> getAllUnit() {
        return unitRepo.findAll();
    }

    @Override
    public void deleteUnit(Long id) {
        unitRepo.deleteById(id);
    }

    @Override
    public Optional<Unit> findUnitById(Long id) {
        Optional<Unit> byId = unitRepo.findById(id);
        if (byId.isEmpty()) {
            return null;
        }
        return byId;
    }

    @Override
    public Unit updateUnit(UnitDTO unitDTO) {
        Optional<Unit> optionalUnit = unitRepo.findById(Long.valueOf(unitDTO.getId()));

        if (optionalUnit.isPresent()) {
            Unit existingUnit = optionalUnit.get();

            existingUnit.setCode(unitDTO.getCode());
            existingUnit.setName(unitDTO.getName());
            existingUnit.setStatus(Unit.Status.valueOf(unitDTO.getStatus()));

            return unitRepo.save(existingUnit);
        }

        return null;
    }

    @Override
    public List<String> findAllUnitIds() {
        return unitRepo.findAllUnitIds();
    }

}
