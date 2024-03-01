package lk.ijse.gdse.taskbackend.service;

import lk.ijse.gdse.taskbackend.dto.CategoryDTO;
import lk.ijse.gdse.taskbackend.entity.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(CategoryDTO categoryDTO);

    List<Category> getAllCategories();
}
