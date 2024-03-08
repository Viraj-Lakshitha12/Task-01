package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.auth.JwtTokenProvider;
import lk.ijse.gdse.taskbackend.dto.ItemDTO;
import lk.ijse.gdse.taskbackend.entity.Item;
import lk.ijse.gdse.taskbackend.service.ItemService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ItemController(ItemService itemService, JwtTokenProvider jwtTokenProvider) {
        this.itemService = itemService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseUtil saveItem(@RequestBody ItemDTO itemDTO, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Item item = itemService.saveItem(itemDTO);
            return new ResponseUtil(200, "save Item", item);
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

    @GetMapping
    public List<Item> getAllItems(@RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            return itemService.getAllItems();
        }
        return new ArrayList<>();
    }

    @PutMapping
    public ResponseUtil updateItem(@RequestBody ItemDTO itemDTO, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Item item = itemService.updateItem(itemDTO);
            return new ResponseUtil(200, "update item", item);
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

    @DeleteMapping("/{id}")
    public ResponseUtil deleteItem(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            itemService.deleteItem(id);
            return new ResponseUtil(200, "Delete Success", null);
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

    @GetMapping("findById/{id}")
    public Item findItemById(@PathVariable Long id) {
        return itemService.findItemById(id).get();
    }

    @GetMapping("/getIds")
    public List<String> getAllItemIds(@RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            return itemService.findAllItemIds();
        }
        return new ArrayList<>();
    }
}
