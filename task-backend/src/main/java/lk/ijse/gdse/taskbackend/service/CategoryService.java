package lk.ijse.gdse.taskbackend.service;

import lk.ijse.gdse.taskbackend.dto.CategoryDTO;
import lk.ijse.gdse.taskbackend.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category saveCategory(CategoryDTO categoryDTO);

    List<Category> getAllCategories();

    void deleteCategory(String id);
    Optional<Category> findCategoryById(String id);

    Category updateCategory(CategoryDTO categoryDTO);
}
