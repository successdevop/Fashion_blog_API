package com.productblog.services;

import com.productblog.dtos.CategoryDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<String> createCategory(CategoryDto CategoryDto);
    ResponseEntity<String> updateCategory(long id, CategoryDto CategoryDto);
    ResponseEntity<List<CategoryDto>> fetchAllCategories();
    ResponseEntity<CategoryDto> findCategory(long id);
    ResponseEntity<String> deleteCategory(long id);
}
