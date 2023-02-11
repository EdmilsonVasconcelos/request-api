package com.request.api.service;

import com.request.api.exception.ProductExistsException;
import com.request.api.exception.ProductNotExistsException;
import com.request.api.model.Category;
import com.request.api.model.Product;
import com.request.api.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {
	
	private static final String PRODUCT_WITH_NAME_EXISTS = "Produto com nome %s já existe";
	
	private static final String PRODUCT_WITH_ID_NOT_EXISTS = "Produto com id %s nao existe";
	
	private final ProductRepository productRepository;

	private final CategoryService categoryService;

	public Product upsertProduct(Product product) {
		if (product.getId() == null) {
			existingProduct(product.getName());
			product.setAvailable(Boolean.FALSE);
		}

		Category category = categoryService.getCategoryById(product.getCategory().getId());

		product.setCategory(category);

		return productRepository.save(product);
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
		existingProduct(idProduct);

		productRepository.deleteById(idProduct);
	}

	public Product getProductById(Long id) {
		existingProduct(id);

		return productRepository.getById(id);

	}

	private void existingProduct(String nameProduct) {
		Optional<Product> product = productRepository.findByName(nameProduct);
		
		if(product.isPresent()) {
			throw new ProductExistsException(String.format(PRODUCT_WITH_NAME_EXISTS, nameProduct));
		}
	}
	
	private void existingProduct(Long id) {
		Optional<Product> product = productRepository.findById(id);
		
		if(product.isEmpty()) {
			throw new ProductNotExistsException(String.format(PRODUCT_WITH_ID_NOT_EXISTS, id));
		}
	}
}
