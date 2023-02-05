package com.request.api.service;

import com.request.api.model.Category;
import com.request.api.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryServiceTest {

    public static final long ID = 1L;
    public static final String CARROS = "Carros";
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category categorySaved;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
        startMocks();
    }

    @Test
    void getAllCategoriesShouldReturnListWithSuccess() {
        when(categoryRepository.findAll()).thenReturn(List.of(categorySaved));

        List<Category> response = categoryService.getAllCategories();

        assertNotNull(response);

        assertEquals(ID, response.get(0).getId());

        assertEquals(CARROS, response.get(0).getName());
    }

    @Test
    void getCategoryByIdShouldReturnCategoryFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(categorySaved));

        Category response = categoryService.getCategoryById(ID);

        assertNotNull(response);

        assertEquals(ID, response.getId());

        assertEquals(CARROS, response.getName());
    }

    @Test
    void upsertCategory() {
    }

    @Test
    void deleteCategory() {
    }

    private void startMocks() {
        categorySaved = new Category(ID, CARROS, LocalDateTime.now(), LocalDateTime.now());
    }
}