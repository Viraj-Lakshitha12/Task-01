package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.dto.ItemDTO;
import lk.ijse.gdse.taskbackend.entity.Item;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/items")
public class ItemController {

    @PostMapping
    public ResponseUtil saveItem(@RequestBody ItemDTO itemDTO) {
        return new ResponseUtil();
    }

    @GetMapping
    public List<Item> getAllItems() {
        return null;
    }

    @PutMapping
    public ResponseUtil updateItem(@RequestBody ItemDTO itemDTO) {
        return new ResponseUtil();
    }

    @DeleteMapping("{/id}")
    public ResponseUtil deleteItem(@PathVariable String id) {
        return new ResponseUtil();
    }
}
