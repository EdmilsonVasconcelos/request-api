package com.request.api.repository;

import java.util.List;
import java.util.Optional;

import com.request.api.model.Category;
import com.request.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Optional<Product> findByName(String nameProduct);

	List<Product> findByCategory(Category category);
}
