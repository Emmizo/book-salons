package com.emmizo.service;

import com.emmizo.dto.SalonDTO;
import com.emmizo.modal.Category;
import com.emmizo.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(salonDTO.getId());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalon(Long id) {
        return categoryRepository.findAllBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            throw new Exception("category not exist with id "+id);
        }
        return  category;
    }

    @Override
    public void deleteCategoryById(Long id, Long salonId) throws Exception {
   Category category = getCategoryById(id);
 if(!category.getSalonId().equals(salonId)){
     throw new RuntimeException("you don't have permission to delete this category "+id);

 }
        categoryRepository.deleteById(id);
    }
}
