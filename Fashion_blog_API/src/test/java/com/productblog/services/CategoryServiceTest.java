package com.productblog.services;

import com.productblog.dtos.CategoryDto;
import com.productblog.exception.CategoryNotFound;
import com.productblog.exception.IllegalCategory;
import com.productblog.models.Category;
import com.productblog.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CategoryServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    ModelMapper modelMapper;
    Category newCategory;


    @BeforeEach
    void setUp() {
        List<Category> categories = new ArrayList<>();
        modelMapper = new ModelMapper();
         newCategory = Category.builder()
                .id(1L)
                 .name("test category")
                .created_at(LocalDateTime.now())
                .modify_at(LocalDateTime.now())
                .build();
        categories.add(newCategory);
        Mockito.when(categoryRepository.save(newCategory)).thenReturn(newCategory);
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(newCategory));
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);

    }

    @Test
    @DisplayName("TEST: create category")
    public void shouldTest_ifCategoryIsCreated(){
        CategoryDto categoryDto = modelMapper.map(newCategory, CategoryDto.class);
        ResponseEntity<String> responseEntity = categoryService.createCategory(categoryDto);

        assertEquals("category created", responseEntity.getBody());
    }

    @Test
    @DisplayName("TEST: validate category inputs")
    public void shouldValidate_CategoryInput(){
        newCategory.setName(null);
        CategoryDto categoryDto = modelMapper.map(newCategory, CategoryDto.class);
        assertThrows(IllegalCategory.class, ()-> categoryService.createCategory(categoryDto), "Category name cannot be null");
    }

    @Test
    @DisplayName("TEST: update category")
    public void shouldTest_ifCategoryIsUpdated(){
        CategoryDto categoryDto = modelMapper.map(newCategory, CategoryDto.class);
        ResponseEntity<String> responseEntity = categoryService.updateCategory(1L, categoryDto);

        assertEquals("category updated", responseEntity.getBody());
    }

    @Test
    @DisplayName("TEST: find category by id and update")
    public void shouldCheck_ifCategoryIsFound(){
        CategoryDto categoryDto = modelMapper.map(newCategory, CategoryDto.class);
        assertThrows(CategoryNotFound.class, ()-> categoryService.updateCategory(2L, categoryDto), "category not found");
    }

    @Test
    @DisplayName("TEST: find by id")
    public void shouldTest_findCategoryById(){
        ResponseEntity<CategoryDto> responseEntity = categoryService.findCategory(1L);
        assertEquals("test category", Objects.requireNonNull(responseEntity.getBody()).getName());
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("TEST: fetch All categories")
    public void shouldTest_findAllCategory(){
        ResponseEntity<List<CategoryDto>> responseEntity = categoryService.fetchAllCategories();
        assertTrue(Objects.requireNonNull(responseEntity.getBody()).size() > 0);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }


    @Test
    @DisplayName("TEST: Delete category")
    public void shouldTest_ifCategoryIsDeleted(){
       ResponseEntity<String> responseEntity =  categoryService.deleteCategory(1L);
       assertEquals("category deleted", responseEntity.getBody());
    }
    @Test
    @DisplayName("TEST: find category by id and update")
    public void shouldCheck_ifCategoryToBeIsFound(){
        assertThrows(CategoryNotFound.class, ()-> categoryService.deleteCategory(2L), "category not found");
    }
}