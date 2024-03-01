package lk.ijse.gdse.taskbackend.service;

import lk.ijse.gdse.taskbackend.dto.SupplierDTO;
import lk.ijse.gdse.taskbackend.entity.Supplier;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    Supplier saveSupplier(SupplierDTO supplierDTO);

    Supplier updateSuppler(SupplierDTO supplierDTO);

    List<Supplier> getAllSuppliers();

    void deleteSupplier(String id);

    Optional<Supplier> findSupplierById(String id);

}
