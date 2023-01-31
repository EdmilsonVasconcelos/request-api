package com.request.api.dto.category.response;

import com.request.api.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
public class CategoryResponseDTO {
    private Long id;

    private String name;

    public static List<CategoryResponseDTO> toList(List<Category> categories) {
        return categories.stream()
                .map(category ->
                        CategoryResponseDTO.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .build())
                .collect(Collectors.toList());
    }

    public static CategoryResponseDTO toCategoryResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
