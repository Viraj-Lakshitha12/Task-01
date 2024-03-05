package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.dto.InventoryDTO;
import lk.ijse.gdse.taskbackend.entity.Inventory;
import lk.ijse.gdse.taskbackend.service.InventoryService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseUtil saveInventory(@RequestBody InventoryDTO inventoryDTO) {
        System.out.println(inventoryDTO);
        Inventory inventory = inventoryService.saveInventory(inventoryDTO);
        return new ResponseUtil(200, "Saved inventory", inventory);
    }

    @GetMapping
    public List<InventoryDTO> getAllInventories() {
        List<InventoryDTO> inventories = inventoryService.getAllInventories();
        //return new ResponseUtil(200, "Retrieved all inventories", inventories);
        return inventories;
    }

    @PutMapping
    public ResponseUtil updateInventory(@RequestBody InventoryDTO inventoryDTO) {
        Inventory updatedInventory = inventoryService.updateInventory(inventoryDTO);
        return new ResponseUtil(200, "Updated inventory", updatedInventory);
    }

    @DeleteMapping("/{id}")
    public ResponseUtil deleteInventory(@PathVariable Long id) {
        String result = inventoryService.deleteInventory(id);
        return new ResponseUtil(200, result, null);
    }
}
