package lk.ijse.gdse.taskbackend.service;

import lk.ijse.gdse.taskbackend.dto.InventoryDTO;
import lk.ijse.gdse.taskbackend.entity.Inventory;

import java.util.List;

public interface InventoryService {
    Inventory saveInventory(InventoryDTO inventoryDTO);

    Inventory updateInventory(InventoryDTO inventoryDTO);

    String deleteInventory(Long id);

    List<InventoryDTO> getAllInventories();


}
