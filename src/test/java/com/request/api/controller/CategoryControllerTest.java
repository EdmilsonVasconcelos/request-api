package com.request.api.controller;

import com.request.api.model.Category;
import com.request.api.service.CategoryService;
import com.request.api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryControllerTest {


    public static final long ID = 1L;

    public static final String CARROS = "Carros";

    public static final String CATEGORIA_EXISTE = "Categoria existe";

    public static final String CATEGORIA_NÃO_EXISTE = "Categoria não existe";

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductService productService;

    private Category categoryToSave;

    private Category category;

    @BeforeEach
    void setUp() {
        categoryController = new CategoryController(categoryService, productService);
        startMocks();
    }

    @Test
    void getAllCategories() {
    }

    @Test
    void getCategoryById() {
    }

    @Test
    void getProductsByCategory() {
    }

    @Test
    void testGetProductsByCategory() {
    }

    @Test
    void saveCategory() {
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }

    private void startMocks() {
        categoryToSave = new Category(null, CARROS, null, null);
        category = new Category(ID, CARROS, LocalDateTime.now(), LocalDateTime.now());
    }
}