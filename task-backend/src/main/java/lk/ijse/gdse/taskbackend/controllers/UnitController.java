package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.auth.JwtTokenProvider;
import lk.ijse.gdse.taskbackend.dto.UnitDTO;
import lk.ijse.gdse.taskbackend.entity.Unit;
import lk.ijse.gdse.taskbackend.service.UnitService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/unit")
public class UnitController {

    private final UnitService unitService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UnitController(UnitService unitService, JwtTokenProvider jwtTokenProvider) {
        this.unitService = unitService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseUtil saveUnit(@RequestBody UnitDTO unitDTO, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Unit unit = unitService.saveUnit(unitDTO);
            return new ResponseUtil(200, "successfully save unit", unit);
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

    @PutMapping
    public ResponseUtil updateUnit(@RequestBody UnitDTO unitDTO, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Unit unit = unitService.updateUnit(unitDTO);
            return new ResponseUtil(200, "successfully update unit", unit);
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

    @GetMapping
    public List<Unit> getAllUnit(@RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            return unitService.getAllUnit();
            //  return new ResponseUtil(200, "fount all unit", allUnit);
        }
        return new ArrayList<>();
    }

    @GetMapping(path = "/getUnitById/{id}")
    public Unit getUnitById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Optional<Unit> unitById = unitService.findUnitById(id);
//        return new ResponseUtil(200, "found unit", unitById);
            return unitById.get();
        }
        return new Unit();
    }


    @DeleteMapping("/{id}")
    public ResponseUtil deleteUnitById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            unitService.deleteUnit(id);
            return new ResponseUtil(200, "successfully Delete unit", null);
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

    @GetMapping(path = "/getIds")
    public List<String> getAllNames(@RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            return unitService.findAllUnitIds();
        }
        return new ArrayList<>();
    }

}
