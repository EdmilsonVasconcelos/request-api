package com.request.api.service;

import com.request.api.exception.ProductExistsException;
import com.request.api.model.Category;
import com.request.api.model.Product;
import com.request.api.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    public static final String CARROS = "Carros";

    public static final String NAME_CAR = "Onix";

    public static final long ID = 1L;

    @InjectMocks
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ProductRepository productRepository;

    private Category category;

    private Product productToSave;

    private Product product;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, categoryService);
        initializeMocks();
    }

    @Test
    void upsertProduct() {
        when(productRepository.findByName(any())).thenReturn(Optional.empty());

        when(categoryService.getCategoryById(anyLong())).thenReturn(category);

        when(productRepository.save(any())).thenReturn(product);

        Product response = productService.upsertProduct(productToSave);

        assertNotNull(response);

        assertEquals(ID, response.getId());

        assertEquals(NAME_CAR, response.getName());

        assertEquals(10.0, response.getPrice());

        assertEquals(12.5, response.getPriceCredit());

        assertEquals(10.0, response.getPriceDebit());

        assertEquals(ID, response.getCategory().getId());

        assertEquals(CARROS, response.getCategory().getName());
    }

    @Test
    void upsertProductShouldReturnExceptionWhenIdIsNullAndNameOfProductAlreadyExist() {
        when(productRepository.findByName(any())).thenReturn(Optional.of(product));

        Exception exception = assertThrows(ProductExistsException.class, () -> productService.upsertProduct(productToSave));

        assertEquals(ProductExistsException.class, exception.getClass());

        assertEquals("Produto com nome Onix já existe", exception.getMessage());

        verify(productRepository, times(1)).findByName(anyString());
    }

    @Test
    void getProductsByCategories() {
    }

    @Test
    void getProductsByCategoryId() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void getProductById() {
    }

    private void initializeMocks() {
        category = Category.builder().id(ID).name(CARROS).build();
        productToSave = buildProductToSave();
        product = buildProduct();
    }

    private Product buildProductToSave() {
        return Product.builder()
                .available(Boolean.TRUE)
                .category(category)
                .name(NAME_CAR)
                .price(10.0)
                .priceCredit(12.5)
                .priceDebit(10.0)
                .build();
    }

    private Product buildProduct() {
        Product product = buildProductToSave();
        product.setId(1L);
        return product;
    }
}