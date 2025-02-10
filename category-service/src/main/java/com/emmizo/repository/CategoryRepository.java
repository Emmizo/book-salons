package com.emmizo.repository;

import com.emmizo.modal.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Set<Category> findAllBySalonId(Long salonId);

}
