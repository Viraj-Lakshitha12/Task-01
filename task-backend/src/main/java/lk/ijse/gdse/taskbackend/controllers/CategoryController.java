package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.dto.CategoryDTO;
import lk.ijse.gdse.taskbackend.entity.Category;
import lk.ijse.gdse.taskbackend.service.CategoryService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/categories")
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseUtil saveCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = categoryService.saveCategory(categoryDTO);
        return new ResponseUtil(200, "successfully save category", category);

    }
    @GetMapping
    public ResponseUtil getAllCategoies(){
        List<Category> allCategories = categoryService.getAllCategories();
        return new ResponseUtil(200, "successfully save category", allCategories);
    }
}
