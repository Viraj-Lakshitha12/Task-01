package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.dto.SupplierDTO;
import lk.ijse.gdse.taskbackend.entity.Supplier;
import lk.ijse.gdse.taskbackend.service.SupplierService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/suppliers")
public class SupplierController {
    @Autowired
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseUtil saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        Supplier supplier = supplierService.saveSupplier(supplierDTO);
        return new ResponseUtil(200, "successfully save supplier", supplier);
    }

    @GetMapping
    public ResponseUtil getAllSuppliers() {
        List<Supplier> allSuppliers = supplierService.getAllSuppliers();
        return new ResponseUtil(200, "found all suppliers", allSuppliers);
    }

    @PutMapping
    public ResponseUtil updateSupplier(@RequestBody SupplierDTO supplierDTO) {
        Supplier supplier = supplierService.updateSuppler(supplierDTO);
        return new ResponseUtil(200, "update suppliers", supplier);
    }

    @GetMapping(path = "/findById", params = {"id"})
    public ResponseUtil findSupplierById(@RequestParam String id) {
        Optional<Supplier> supplierById = supplierService.findSupplierById(id);
        return new ResponseUtil(200, "find suppliers", supplierById);
    }

    @DeleteMapping(params = {"id"})
    public ResponseUtil deleteSupplier(@RequestParam String id) {
        supplierService.deleteSupplier(id);
        return new ResponseUtil(200, "found all suppliers", null);
    }
}
