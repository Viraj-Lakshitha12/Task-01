package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.dto.UnitDTO;
import lk.ijse.gdse.taskbackend.entity.Unit;
import lk.ijse.gdse.taskbackend.service.UnitService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/unit")
public class UnitController {
    @Autowired
    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @PostMapping
    public ResponseUtil saveUnit(@RequestBody UnitDTO unitDTO) {
        Unit unit = unitService.saveUnit(unitDTO);
        return new ResponseUtil(200, "successfully save unit", unit);
    }

    @PutMapping
    public ResponseUtil updateUnit(@RequestBody UnitDTO unitDTO) {
        Unit unit = unitService.updateUnit(unitDTO);
        return new ResponseUtil(200, "successfully update unit", unit);
    }

    @GetMapping
    public List<Unit> getAllUnit() {
        List<Unit> allUnit = unitService.getAllUnit();
        //  return new ResponseUtil(200, "fount all unit", allUnit);
        return allUnit;
    }

    @GetMapping(path = "/getUnitById", params = {"id"})
    public ResponseUtil getUnitById(@RequestParam String id) {
        Optional<Unit> unitById = unitService.findUnitById(id);
        return new ResponseUtil(200, "found unit", unitById);
    }

    @DeleteMapping("/{id}")
    public ResponseUtil deleteUnitById(@PathVariable String id) {
        unitService.deleteUnit(id);
        return new ResponseUtil(200, "successfully Delete unit", null);
    }

}
