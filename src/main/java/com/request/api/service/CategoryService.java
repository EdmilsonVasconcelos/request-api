package com.request.api.service;

import com.request.api.dto.category.request.CategoryRequestDTO;
import com.request.api.exception.GenericException;
import com.request.api.model.Category;
import com.request.api.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new GenericException("Categoria não existe"));
    }

    public Category upsertCategory(CategoryRequestDTO request) {
        checkIfItExists(request.getName());

        Category categoryToSave = Category.toDomain(request);

        return categoryRepository.save(categoryToSave);
    }

    public void deleteCategory(Long id) {
        checkIfItExists(id);

        categoryRepository.deleteById(id);
    }

    private void checkIfItExists(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if(category.isEmpty()) {
            throw new GenericException("Categoria não existe");
        }
    }

    private void checkIfItExists(String name) {
        Optional<Category> category = categoryRepository.findByName(name);

        if(category.isPresent()) {
            throw new GenericException("Categoria existe");
        }
    }
}
