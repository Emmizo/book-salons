package com.emmizo.service;

import com.emmizo.dto.SalonDTO;
import com.emmizo.modal.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    Category saveCategory(Category category, SalonDTO salonDTO);
    Set<Category> getAllCategoriesBySalon(Long id);
    Category getCategoryById(Long id) throws Exception;
    void deleteCategoryById(Long id, Long salonId) throws Exception;

}
