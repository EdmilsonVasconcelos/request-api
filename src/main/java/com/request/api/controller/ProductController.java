package com.request.api.controller;

import com.request.api.dto.product.request.ProductRequestDTO;
import com.request.api.dto.product.response.ProductResponseDTO;
import com.request.api.model.Product;
import com.request.api.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.request.api.dto.product.response.ProductResponseDTO.toList;
import static com.request.api.dto.product.response.ProductResponseDTO.toProductResponseDTO;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(toList(products));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(toProductResponseDTO(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> saveProduct(@Valid ProductRequestDTO request) {
        Product productSaved = productService.upsertProduct(request);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productSaved.getId())
                .toUri();

        return ResponseEntity.created(uri).body(toProductResponseDTO(productSaved));
    }

    @PutMapping
    public ResponseEntity<ProductResponseDTO> updateProduct(@Valid ProductRequestDTO request) {
        Product productUpdated = productService.upsertProduct(request);
        return ResponseEntity.ok(toProductResponseDTO(productUpdated));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
