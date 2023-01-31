package com.request.api.dto.customer.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.request.api.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerRequestDTO {

	@NotNull(message = "O celular do cliente é obrigatório")
	@Size(message = "O celular deve ter entre 9 e 15 caracteres", min = 9, max = 15)
	private String phoneNumber;
	
	@NotNull(message = "O endereco do cliente é obrigatório")
	@Size(message = "O endereco deve ter entre 10 e 255 caracteres", min = 10, max = 255)
	private Address address;
}
