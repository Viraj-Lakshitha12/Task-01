package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.auth.JwtTokenProvider;
import lk.ijse.gdse.taskbackend.dto.SupplierDTO;
import lk.ijse.gdse.taskbackend.entity.Supplier;
import lk.ijse.gdse.taskbackend.service.SupplierService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SupplierController(SupplierService supplierService, JwtTokenProvider jwtTokenProvider) {
        this.supplierService = supplierService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseUtil saveSupplier(@RequestBody SupplierDTO supplierDTO, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Supplier supplier = supplierService.saveSupplier(supplierDTO);
            return new ResponseUtil(200, "successfully save supplier", supplier);
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

    @GetMapping
    public List<Supplier> getAllSuppliers(@RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            return supplierService.getAllSuppliers();
            //return new ResponseUtil(200, "found all suppliers", allSuppliers);

        }
        return new ArrayList<>();
    }

    @PutMapping
    public ResponseUtil updateSupplier(@RequestBody SupplierDTO supplierDTO, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Supplier supplier = supplierService.updateSuppler(supplierDTO);
            return new ResponseUtil(200, "update suppliers", supplier);
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

    @GetMapping(path = "/findById", params = {"id"})
    public ResponseUtil findSupplierById(@RequestParam String id, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Optional<Supplier> supplierById = supplierService.findSupplierById(id);
            return new ResponseUtil(200, "find suppliers", supplierById);
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

    @DeleteMapping("/{id}")
    public ResponseUtil deleteSupplier(@PathVariable String id, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            String result = supplierService.deleteSupplier(id);
            if ("ok".equals(result)) {
                return new ResponseUtil(200, "Supplier deleted successfully", null);
            } else {
                return new ResponseUtil(404, "Supplier not found or error during deletion", null);
            }
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

}
