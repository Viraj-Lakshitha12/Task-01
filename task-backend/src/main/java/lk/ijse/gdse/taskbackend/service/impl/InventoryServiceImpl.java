package lk.ijse.gdse.taskbackend.service.impl;

import lk.ijse.gdse.taskbackend.dto.InventoryDTO;
import lk.ijse.gdse.taskbackend.entity.Inventory;
import lk.ijse.gdse.taskbackend.entity.Item;
import lk.ijse.gdse.taskbackend.repository.InventoryRepo;
import lk.ijse.gdse.taskbackend.service.InventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepo inventoryRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public InventoryServiceImpl(InventoryRepo inventoryRepo, ModelMapper modelMapper) {
        this.inventoryRepo = inventoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Inventory saveInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = modelMapper.map(inventoryDTO, Inventory.class);
        return inventoryRepo.save(inventory);
    }

    @Override
    public Inventory updateInventory(InventoryDTO inventoryDTO) {
        Optional<Inventory> optionalInventory = inventoryRepo.findById(Long.valueOf(inventoryDTO.getId()));

        if (optionalInventory.isPresent()) {
            Inventory existingInventory = optionalInventory.get();
            existingInventory.setItem(modelMapper.map(inventoryDTO, Item.class));
            existingInventory.setReceivedQty(inventoryDTO.getReceivedQty());
            existingInventory.setReceivedDate(inventoryDTO.getReceivedDate());
            existingInventory.setStatus(Inventory.Status.valueOf(inventoryDTO.getStatus()));
            existingInventory.setApprovalStatus(Inventory.ApprovalStatus.valueOf(inventoryDTO.getApprovalStatus()));

            return inventoryRepo.save(existingInventory);
        } else {
            throw new RuntimeException("Inventory not found with ID: " + inventoryDTO.getId());
        }
    }

    @Override
    public String deleteInventory(Long id) {
        Optional<Inventory> optionalInventory = inventoryRepo.findById(id);

        if (optionalInventory.isPresent()) {
            inventoryRepo.deleteById(id);
            return "Inventory deleted successfully";
        } else {
            throw new RuntimeException("Inventory not found with ID: " + id);
        }
    }

    @Override
    public List<InventoryDTO> getAllInventories() {
        List<Inventory> inventories = inventoryRepo.findAll();
        return inventories.stream()
                .map(inventory -> modelMapper.map(inventory, InventoryDTO.class))
                .collect(Collectors.toList());
    }
}
