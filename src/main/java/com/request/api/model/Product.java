package com.request.api.model;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.request.api.dto.product.request.ProductRequestDTO;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String name;
	
	private Double price;
	
	private Double priceDebit;
	
	private Double priceCredit;
	
	private String description;
	
	private Boolean available;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
    @CreatedDate
    @Column(updatable = false)
	private LocalDateTime created;
		
    @LastModifiedDate
	private LocalDateTime updated;

    public static Product toDomain(ProductRequestDTO productToSaveRequestDTO) {
		return Product.builder()
				.id(productToSaveRequestDTO.getId())
				.name(productToSaveRequestDTO.getName())
				.price(productToSaveRequestDTO.getPrice())
				.priceDebit(productToSaveRequestDTO.getPriceDebit())
				.priceCredit(productToSaveRequestDTO.getPriceCredit())
				.description(productToSaveRequestDTO.getDescription())
				.available(productToSaveRequestDTO.getAvailable())
				.build();
	}
}
