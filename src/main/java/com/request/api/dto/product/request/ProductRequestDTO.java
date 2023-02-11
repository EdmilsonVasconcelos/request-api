package com.request.api.dto.product.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {

	private Long id;
	
	@NotNull(message = "O nome é obrigatório")
	@Size(message = "O nome deve ter entre 2 e 50 caracteres", min = 2, max = 90)
	private String name;
	
	@NotNull(message = "O preco é obrigatório")
	private Double price;
	
	private Double priceDebit;
	
	private Double priceCredit;

	private String description;
	
	private Boolean available;
}