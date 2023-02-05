package com.request.api.service;

import com.request.api.exception.GenericException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTest {

    public static final long ID = 1L;
    public static final String CARROS = "Carros";
    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private Category categoryToSave;

    private Category category;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
        startMocks();
    }

    @Test
    void getAllCategoriesShouldReturnListWithSuccess() {
        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<Category> response = categoryService.getAllCategories();

        assertNotNull(response);

        assertEquals(ID, response.get(0).getId());

        assertEquals(CARROS, response.get(0).getName());
    }

    @Test
    void getCategoryByIdShouldReturnCategoryFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        Category response = categoryService.getCategoryById(ID);

        assertNotNull(response);

        assertEquals(ID, response.getId());

        assertEquals(CARROS, response.getName());
    }

    @Test
    void getCategoryByIdShouldReturnExceptionWhenCategoryDoesNotExist() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(GenericException.class, () -> categoryService.getCategoryById(ID));

        assertEquals(GenericException.class, exception.getClass());

        assertEquals("Categoria não existe", exception.getMessage());

        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    void upsertCategoryShouldSaveNewCategory() {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

        when(categoryRepository.save(any())).thenReturn(category);

        Category response = categoryService.upsertCategory(categoryToSave);

        assertNotNull(response);

        assertEquals(ID, response.getId());

        assertEquals(CARROS, response.getName());

    }

    @Test
    void upsertCategoryShouldUpdateCategory() {
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());

        when(categoryRepository.save(any())).thenReturn(category);

        Category response = categoryService.upsertCategory(category);

        assertNotNull(response);

        assertEquals(ID, response.getId());

        assertEquals(CARROS, response.getName());

    }

    @Test
    void deleteCategory() {
    }

    private void startMocks() {
        categoryToSave = new Category(null, CARROS, null, null);
        category = new Category(ID, CARROS, LocalDateTime.now(), LocalDateTime.now());
    }
}