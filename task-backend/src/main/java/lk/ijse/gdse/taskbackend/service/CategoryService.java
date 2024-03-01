package lk.ijse.gdse.taskbackend.service;

import lk.ijse.gdse.taskbackend.dto.CategoryDTO;
import lk.ijse.gdse.taskbackend.entity.Category;

public interface CategoryService {
    Category saveCategory(CategoryDTO categoryDTO);

}
