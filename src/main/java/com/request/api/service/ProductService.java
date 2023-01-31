package com.request.api.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.request.api.dto.product.request.ProductRequestDTO;
import com.request.api.exception.ProductExistsException;
import com.request.api.exception.ProductNotExistsException;
import com.request.api.model.Category;
import com.request.api.model.Product;
import com.request.api.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {
	
	private static final String PRODUCT_WITH_NAME_EXISTS = "Produto com nome %s já existe";
	
	private static final String PRODUCT_WITH_ID_NOT_EXISTS = "Produto com id %s nao existe";
	
	private final ProductRepository productRepository;

	private final CategoryService categoryService;

	public Product upsertProduct(ProductRequestDTO request) {
		if(request.getId() == null){
			checkIfExistsProduct(request.getName());
		}

		Category category = categoryService.getCategoryById(request.getCategoryId());

		Product productToSave = Product.toDomain(request);

		productToSave.setCategory(category);

		if (request.getId() == null) {
			productToSave.setAvailable(Boolean.FALSE);
		}

		return productRepository.save(productToSave);
	}

	public Map<String, List<Product>> getProductsByCategories() {
		List<Category> categories = categoryService.getAllCategories();
		return categories.stream()
				.collect(Collectors.toMap(Category::getName, productRepository::findByCategory));
	}

	public List<Product> getProductsByCategoryId(Long categoryId) {
		Category category = categoryService.getCategoryById(categoryId);
		return productRepository.findByCategory(category);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}
	
	public void deleteProduct(Long idProduct) {
		checkIfExistsProduc(idProduct);

		productRepository.deleteById(idProduct);
	}

	public Product getProductById(Long id) {
		checkIfExistsProduc(id);

		return productRepository.getById(id);

	}

	private void checkIfExistsProduct(String nameProduct) {
		Optional<Product> product = productRepository.findByName(nameProduct);
		
		if(product.isPresent()) {
			throw new ProductExistsException(String.format(PRODUCT_WITH_NAME_EXISTS, nameProduct));
		}
	}
	
	private void checkIfExistsProduc(Long id) {
		Optional<Product> product = productRepository.findById(id);
		
		if(product.isEmpty()) {
			throw new ProductNotExistsException(String.format(PRODUCT_WITH_ID_NOT_EXISTS, id));
		}
	}
}
