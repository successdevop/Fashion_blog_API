package com.productblog.services.impl;

import com.productblog.dtos.CategoryDto;
import com.productblog.exception.CategoryNotFound;
import com.productblog.exception.IllegalCategory;
import com.productblog.models.Category;
import com.productblog.repositories.CategoryRepository;
import com.productblog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        modelMapper = new ModelMapper();
    }

    @Override
    public ResponseEntity<String> createCategory(CategoryDto categoryDto) {

        Optional.ofNullable(categoryDto.getName())
                .orElseThrow(() -> new IllegalCategory("Category name cannot be null"));

        Category category = modelMapper.map(categoryDto, Category.class);
        category.setCreated_at(LocalDateTime.now());
        category.setModify_at(LocalDateTime.now());
        categoryRepository.save(category);
       return new ResponseEntity<>("category created", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> updateCategory(long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound("category not found"));

        category.setName(categoryDto.getName());
        categoryRepository.save(category);
        return new ResponseEntity<>("category updated", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> deleteCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new CategoryNotFound("category not found"));
        categoryRepository.delete(category);
        return new ResponseEntity<>("category deleted", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<CategoryDto>> fetchAllCategories() {
        List<CategoryDto> categoryDtos =  new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for (Category category: categories)
            categoryDtos.add(modelMapper.map(category, CategoryDto.class));

        return new ResponseEntity<>(categoryDtos,HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<CategoryDto> findCategory(long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new CategoryNotFound("category not found"));
        CategoryDto categoryDto = modelMapper.map(category,CategoryDto.class);

       return  new ResponseEntity<>(categoryDto,HttpStatus.ACCEPTED);
    }
}
