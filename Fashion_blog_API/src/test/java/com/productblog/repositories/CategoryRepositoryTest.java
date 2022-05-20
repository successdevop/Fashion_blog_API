package com.productblog.repositories;

import com.productblog.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@DataJpaTest
//@TestPropertySource(locations = "classpath:com/productblog/test.properties")
//@AutoConfigureTestDatabase(replace = Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TestEntityManager entityManager;

    Category category;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Cloths")
                .created_at(LocalDateTime.now())
                .modify_at(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("TEST: save")
    @Rollback(value = false)
    public void shouldTest_ifCategoryWasSaved(){

       Category category1 =  categoryRepository.save(category);
       assertThat(category1).isNotNull();
    }

    @Test
    @DisplayName("TEST: find by id")
    public void shouldTest_findById(){
        Category category1 =  categoryRepository.findById(1L).get();
        assertThat(category1).isNotNull();
    }

}