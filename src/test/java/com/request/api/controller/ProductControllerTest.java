package com.request.api.controller;

import com.request.api.dto.product.response.ProductResponseDTO;
import com.request.api.model.Category;
import com.request.api.model.Product;
import com.request.api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    public static final String ONIX = "Onix";

    public static final long ID = 1L;

    public static final String CARROS = "Carros";

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private Product product;

    private Category category;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productService);
        initializeMocks();
    }

    @Test
    void getAllProducts() {
        when(productService.getAllProducts()).thenReturn(List.of(product));

        ResponseEntity<List<ProductResponseDTO>> response = productController.getAllProducts();

        assertNotNull(response);

        assertEquals(1, response.getBody().size());

        assertEquals(ID, response.getBody().get(0).getId());

        assertEquals(ONIX, response.getBody().get(0).getName());
    }

    @Test
    void getProductById() {
        when(productService.getProductById(anyLong())).thenReturn(product);

        ResponseEntity<ProductResponseDTO> response = productController.getProductById(ID);

        assertNotNull(response);

        assertEquals(ID, response.getBody().getId());

        assertEquals(ONIX, response.getBody().getName());
    }

    @Test
    void saveProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void deleteProduct() {

    }

    private void initializeMocks() {
        category = Category.builder().id(ID).name(CARROS).build();
        product = buildProduct();
    }

    private Product buildProduct() {
        return Product.builder()
                .id(ID)
                .available(Boolean.TRUE)
                .category(category)
                .name(ONIX)
                .price(10.0)
                .priceCredit(12.5)
                .priceDebit(10.0)
                .build();
    }
}