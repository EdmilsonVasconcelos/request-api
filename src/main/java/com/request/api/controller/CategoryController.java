package com.request.api.controller;

import com.request.api.dto.category.request.CategoryRequestDTO;
import com.request.api.dto.category.response.CategoryResponseDTO;
import com.request.api.dto.product.response.ProductResponseDTO;
import com.request.api.dto.product.response.ProductsByCategoryResponseDTO;
import com.request.api.model.Category;
import com.request.api.model.Product;
import com.request.api.service.CategoryService;
import com.request.api.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.request.api.dto.category.response.CategoryResponseDTO.toCategoryResponseDTO;
import static com.request.api.dto.category.response.CategoryResponseDTO.toList;

@AllArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();

        return ResponseEntity.ok(toList(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);

        return ResponseEntity.ok(toCategoryResponseDTO(category));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductsByCategoryResponseDTO>> getProductsByCategory() {
        Map<String, List<Product>> listProducts = productService.getProductsByCategories();

        List<ProductsByCategoryResponseDTO> productsByCategory = listProducts.entrySet()
                .stream()
                .map(e -> ProductsByCategoryResponseDTO.builder()
                        .category(e.getKey())
                        .products(ProductResponseDTO.toList(e.getValue()))
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(productsByCategory);
    }

    @GetMapping("/productsByCategory")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@RequestParam Long categoryId) {
        List<Product> products = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(ProductResponseDTO.toList(products));
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> saveCategory(@Valid CategoryRequestDTO categoryRequestDTO) {
        Category categorySaved = categoryService.upsertCategory(categoryRequestDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categorySaved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(toCategoryResponseDTO(categorySaved));
    }

    @PutMapping
    public ResponseEntity<CategoryResponseDTO> updateCategory(@Valid CategoryRequestDTO categoryRequestDTO) {
        Category categoryUpdated = categoryService.upsertCategory(categoryRequestDTO);

        return ResponseEntity.ok(toCategoryResponseDTO(categoryUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}
