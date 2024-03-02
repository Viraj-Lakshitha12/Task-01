package lk.ijse.gdse.taskbackend.service.impl;

import lk.ijse.gdse.taskbackend.dto.SupplierDTO;
import lk.ijse.gdse.taskbackend.entity.Supplier;
import lk.ijse.gdse.taskbackend.repository.SupplierRepo;
import lk.ijse.gdse.taskbackend.service.SupplierService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private final SupplierRepo supplierRepo;

    @Autowired
    private final ModelMapper modelMapper;


    public SupplierServiceImpl(SupplierRepo supplierRepo, ModelMapper modelMapper) {
        this.supplierRepo = supplierRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Supplier saveSupplier(SupplierDTO supplierDTO) {
        return supplierRepo.save(modelMapper.map(supplierDTO, Supplier.class));
    }

    @Override
    public Supplier updateSuppler(SupplierDTO updatedSupplier) {
        Optional<Supplier> existingSupplierOptional = supplierRepo.findById(String.valueOf(updatedSupplier.getId()));

        if (existingSupplierOptional.isPresent()) {
            Supplier existingSupplier = existingSupplierOptional.get();
            // Update the fields
            existingSupplier.setName(updatedSupplier.getName());
            existingSupplier.setAddress(updatedSupplier.getAddress());
            existingSupplier.setSupplierCode(updatedSupplier.getSupplierCode());
            existingSupplier.setStatus(Supplier.Status.valueOf(updatedSupplier.getStatus()));

            return supplierRepo.save(existingSupplier);
        } else {
            return null;
        }
    }



    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepo.findAll();
    }

    @Override
    public void deleteSupplier(String id) {
        supplierRepo.deleteById(id);
    }

    @Override
    public Optional<Supplier> findSupplierById(String id) {
        return supplierRepo.findById(id);
    }
}
