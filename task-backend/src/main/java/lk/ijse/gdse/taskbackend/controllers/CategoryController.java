package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.auth.JwtTokenProvider;
import lk.ijse.gdse.taskbackend.dto.CategoryDTO;
import lk.ijse.gdse.taskbackend.entity.Category;
import lk.ijse.gdse.taskbackend.service.CategoryService;
import lk.ijse.gdse.taskbackend.service.InventoryService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final InventoryService inventoryService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public CategoryController(CategoryService categoryService, InventoryService inventoryService, JwtTokenProvider jwtTokenProvider) {
        this.categoryService = categoryService;
        this.inventoryService = inventoryService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseUtil saveCategory(@RequestBody CategoryDTO categoryDTO, @RequestHeader("Authorization") String authorizationHeader) {
        System.out.println(categoryDTO);
        Category category = categoryService.saveCategory(categoryDTO);
        return new ResponseUtil(200, "successfully save category", category);

    }

    @GetMapping
    public List<Category> getAllCategoies(@RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            return categoryService.getAllCategories();
        }
//        return new ResponseUtil(200, "found all category", allCategories);
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseUtil deleteCategory(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            categoryService.deleteCategory(id);
            return new ResponseUtil(200, "Successfully deleted category", null);
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

    @GetMapping("/findById/{id}")
    public Category findCategoryById(@PathVariable Long id, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Optional<Category> categoryById = categoryService.findCategoryById(id);
            //return new ResponseUtil(200, "successfully find category", categoryById);
            return categoryById.get();
        }
        return null;
    }

    @PutMapping
    public ResponseUtil updateCategory(@RequestBody CategoryDTO categoryDTO, @RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            Category category = categoryService.updateCategory(categoryDTO);
            if (category != null) {
                return new ResponseUtil(200, "successfully update category", category);
            } else {
                return new ResponseUtil(200, "not found category", null);
            }
        }
        return new ResponseUtil(401, "UnAuthorization", null);
    }

    @GetMapping(path = "/getIds")
    public List<String> getAllNames(@RequestHeader("Authorization") String authorizationHeader) {
        if (jwtTokenProvider.validateToken(authorizationHeader)) {
            return categoryService.getAllCategoryIds();
        }
        return null;
    }
}
