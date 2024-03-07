package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.auth.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public InventoryController(InventoryService inventoryService, JwtTokenProvider jwtTokenProvider) {
        this.inventoryService = inventoryService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseUtil saveInventory(@RequestBody InventoryDTO inventoryDTO, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Inventory inventory = inventoryService.saveInventory(inventoryDTO);
            return new ResponseUtil(200, "Saved inventory", inventory);
        } else {
            return new ResponseUtil(401, "Invalid token", null);
        }
    }

    private String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    @GetMapping
    public List<InventoryDTO> getAllInventories(@RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            return inventoryService.getAllInventories();
        } else {
            System.out.println("wd");
            return null;
        }
    }

    @PutMapping
    public ResponseUtil updateInventory(@RequestBody InventoryDTO inventoryDTO,@RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Inventory updatedInventory = inventoryService.updateInventory(inventoryDTO);
            return new ResponseUtil(200, "Updated inventory", updatedInventory);
        }
        return new ResponseUtil(401, "UnAuthorization", null);

    }

    @DeleteMapping("/{id}")
    public ResponseUtil deleteInventory(@PathVariable Long id,@RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            String result = inventoryService.deleteInventory(id);
            return new ResponseUtil(200, result, null);
        }
        return new ResponseUtil(200, "UnAuthorization", null);
    }
}
