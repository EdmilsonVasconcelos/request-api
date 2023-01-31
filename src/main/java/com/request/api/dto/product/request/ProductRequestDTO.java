package com.request.api.dto.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

	private Long id;
	
	@NotNull(message = "O nome é obrigatório")
	@Size(message = "O nome deve ter entre 2 e 50 caracteres", min = 2, max = 90)
	private String name;
	
	@NotNull(message = "O preco é obrigatório")
	private Double price;
	
	private Double priceDebit;
	
	private Double priceCredit;

	@NotNull(message = "O id da categoria é obrigatório")
	private Long categoryId;
	
	private String description;
	
	private Boolean available;
}