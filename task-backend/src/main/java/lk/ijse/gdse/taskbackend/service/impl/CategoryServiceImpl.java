package lk.ijse.gdse.taskbackend.service.impl;

import lk.ijse.gdse.taskbackend.dto.CategoryDTO;
import lk.ijse.gdse.taskbackend.entity.Category;
import lk.ijse.gdse.taskbackend.repository.CategoryRepo;
import lk.ijse.gdse.taskbackend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private final CategoryRepo categoryRepo;
    @Autowired
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepo categoryRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public Category saveCategory(CategoryDTO categoryDTO) {
        return categoryRepo.save(modelMapper.map(categoryDTO, Category.class));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepo.deleteById(id);
    }

    @Override
    public Optional<Category> findCategoryById(Long id) {
        return categoryRepo.findById(id);
    }

    @Override
    public Category updateCategory(CategoryDTO categoryDTO) {
        System.out.println(categoryDTO);
        Optional<Category> byId = categoryRepo.findById(categoryDTO.getId());
        if (byId.isPresent()) {
            return categoryRepo.save(modelMapper.map(categoryDTO, Category.class));
        }
        return null;
    }

}
