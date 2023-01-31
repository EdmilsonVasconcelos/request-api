package com.request.api.dto.product.response;

import com.request.api.dto.category.response.CategoryResponseDTO;
import com.request.api.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.request.api.dto.category.response.CategoryResponseDTO.toCategoryResponseDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
	
	private Long id;
	
	private String name;

	private String description;

	private boolean available;

	private Double price;

	private Double priceCredit;

	private Double priceDebit;

	private CategoryResponseDTO category;

	public static List<ProductResponseDTO> toList(List<Product> products) {
		return products.stream()
				.map(product ->
						ProductResponseDTO.builder()
							.price(product.getPrice())
							.priceDebit(product.getPriceDebit())
							.priceCredit(product.getPriceCredit())
							.name(product.getName())
							.id(product.getId())
							.description(product.getDescription())
							.available(product.getAvailable())
							.category(toCategoryResponseDTO(product.getCategory()))
							.build())
				.collect(Collectors.toList());
	}

	public static ProductResponseDTO toProductResponseDTO(Product product) {
		return ProductResponseDTO.builder()
				.price(product.getPrice())
				.priceDebit(product.getPriceDebit())
				.priceCredit(product.getPriceCredit())
				.name(product.getName())
				.id(product.getId())
				.available(product.getAvailable())
				.description(product.getDescription())
				.category(toCategoryResponseDTO(product.getCategory()))
				.build();
	}
}
