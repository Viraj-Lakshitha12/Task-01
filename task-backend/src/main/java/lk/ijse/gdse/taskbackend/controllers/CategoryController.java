package lk.ijse.gdse.taskbackend.controllers;

import lk.ijse.gdse.taskbackend.dto.CategoryDTO;
import lk.ijse.gdse.taskbackend.entity.Category;
import lk.ijse.gdse.taskbackend.service.CategoryService;
import lk.ijse.gdse.taskbackend.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseUtil saveCategory(@RequestBody CategoryDTO categoryDTO) {
        System.out.println(categoryDTO);
        Category category = categoryService.saveCategory(categoryDTO);
        return new ResponseUtil(200, "successfully save category", category);

    }

    @GetMapping
    public List<Category> getAllCategoies() {
        List<Category> allCategories = categoryService.getAllCategories();
//        return new ResponseUtil(200, "found all category", allCategories);
        return allCategories;
    }

    @DeleteMapping("/{id}")
    public ResponseUtil deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseUtil(200, "Successfully deleted category", null);
    }

    @GetMapping(value = "/findById", params = {"id"})
    public ResponseUtil findCategoryById(@RequestParam Long id) {
        Optional<Category> categoryById = categoryService.findCategoryById(id);
        return new ResponseUtil(200, "successfully find category", categoryById);
    }

    @PutMapping
    public ResponseUtil updateCategory(@RequestBody CategoryDTO categoryDTO) {
        System.out.println(categoryDTO);
        System.out.println("ok 1");
        Category category = categoryService.updateCategory(categoryDTO);
        System.out.println("ok 2");
        if (category != null) {
            System.out.println("ok 3");
            return new ResponseUtil(200, "successfully update category", category);
        } else {
            return new ResponseUtil(200, "not found category", null);
        }

    }

    @GetMapping(path = "/getNames")
    public List<String> getAllNames() {
        return categoryService.getAllCategoryNames();
    }
}
