package com.request.api.controller;

import com.request.api.dto.category.request.CategoryRequestDTO;
import com.request.api.dto.category.response.CategoryResponseDTO;
import com.request.api.dto.product.response.ProductResponseDTO;
import com.request.api.dto.product.response.ProductsByCategoryResponseDTO;
import com.request.api.model.Category;
import com.request.api.model.Product;
import com.request.api.service.CategoryService;
import com.request.api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class CategoryControllerTest {

    public static final long ID = 1L;

    public static final String CARROS = "Carros";

    public static final String ONIX = "Onix";

    public static final String DESCRIPTION = "Muitíssimo bom";

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductService productService;

    private Category category;

    private CategoryRequestDTO categoryRequestDTO;

    private Product product;

    @BeforeEach
    void setUp() {
        categoryController = new CategoryController(categoryService, productService);
        startMocks();
    }

    @Test
    void getAllCategories() {
        when(categoryService.getAllCategories()).thenReturn(List.of(category));

        ResponseEntity<List<CategoryResponseDTO>> response = categoryController.getAllCategories();

        assertNotNull(response);

        assertEquals(1, response.getBody().size());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(CARROS, response.getBody().get(0).getName());
    }

    @Test
    void getCategoryById() {
        when(categoryService.getCategoryById(anyLong())).thenReturn(category);

        ResponseEntity<CategoryResponseDTO> response = categoryController.getCategoryById(ID);

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(CARROS, response.getBody().getName());
    }

    @Test
    void getProductsByCategories() {
        Map<String, List<Product>> productsByCategory = new HashMap<>();

        productsByCategory.put(category.getName(), List.of(product));

        when(productService.getProductsByCategories()).thenReturn(productsByCategory);

        ResponseEntity<List<ProductsByCategoryResponseDTO>> response = categoryController.getProductsByCategories();

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(CARROS, response.getBody().get(0).getCategory());

        assertEquals(ONIX, response.getBody().get(0).getProducts().get(0).getName());

        assertTrue(response.getBody().get(0).getProducts().get(0).isAvailable());

        assertEquals(10.0, response.getBody().get(0).getProducts().get(0).getPrice());

        assertEquals(12.5, response.getBody().get(0).getProducts().get(0).getPriceCredit());

        assertEquals(10.0, response.getBody().get(0).getProducts().get(0).getPriceDebit());

        assertEquals(ID, response.getBody().get(0).getProducts().get(0).getId());

        assertEquals(DESCRIPTION, response.getBody().get(0).getProducts().get(0).getDescription());
    }

    @Test
    void getProductsByCategoryId() {
        when(productService.getProductsByCategoryId(anyLong())).thenReturn(List.of(product));

        ResponseEntity<List<ProductResponseDTO>> response = categoryController.getProductsByCategoryId(ID);

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(CARROS, response.getBody().get(0).getCategory().getName());

        assertEquals(ID, response.getBody().get(0).getCategory().getId());

        assertEquals(ONIX, response.getBody().get(0).getName());

        assertTrue(response.getBody().get(0).isAvailable());

        assertEquals(10.0, response.getBody().get(0).getPrice());

        assertEquals(12.5, response.getBody().get(0).getPriceCredit());

        assertEquals(10.0, response.getBody().get(0).getPriceDebit());

        assertEquals(ID, response.getBody().get(0).getId());

        assertEquals(DESCRIPTION, response.getBody().get(0).getDescription());
    }

    @Test
    void saveCategory() {
        when(categoryService.upsertCategory(any())).thenReturn(category);

        ResponseEntity<CategoryResponseDTO> response = categoryController.saveCategory(categoryRequestDTO);

        assertNotNull(response);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());

        assertEquals(CARROS, response.getBody().getName());
    }

    @Test
    void updateCategory() {
        when(categoryService.upsertCategory(any())).thenReturn(category);

        ResponseEntity<CategoryResponseDTO> response = categoryController.updateCategory(new CategoryRequestDTO(1L, CARROS));

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(ID, response.getBody().getId());

        assertEquals(CARROS, response.getBody().getName());
    }

    @Test
    void deleteCategory() {
        doNothing().when(categoryService).deleteCategory(anyLong());

        ResponseEntity<Void> response = categoryController.deleteCategory(ID);

        assertNotNull(response);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(categoryService, times(1)).deleteCategory(anyLong());
    }

    private void startMocks() {
        category = new Category(ID, CARROS, LocalDateTime.now(), LocalDateTime.now());
        categoryRequestDTO = new CategoryRequestDTO(null, CARROS);
        product = Product.builder()
                .id(ID)
                .name(ONIX)
                .price(10.0)
                .priceCredit(12.5)
                .priceDebit(10.0)
                .description(DESCRIPTION)
                .available(Boolean.TRUE)
                .category(category)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .build();
    }
}