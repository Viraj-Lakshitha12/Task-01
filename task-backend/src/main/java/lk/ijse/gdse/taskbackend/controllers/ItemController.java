package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.dto.ItemDTO;
import lk.ijse.gdse.taskbackend.entity.Item;
import lk.ijse.gdse.taskbackend.service.ItemService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/items")
public class ItemController {
    @Autowired
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseUtil saveItem(@RequestBody ItemDTO itemDTO) {
        Item item = itemService.saveItem(itemDTO);
        return new ResponseUtil(200, "save Item", item);
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PutMapping
    public ResponseUtil updateItem(@RequestBody ItemDTO itemDTO) {
        Item item = itemService.updateItem(itemDTO);
        return new ResponseUtil(200, "update item", item);
    }

    @DeleteMapping("/{id}")
    public ResponseUtil deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return new ResponseUtil();
    }
}
