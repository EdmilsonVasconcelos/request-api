package com.request.api.dto.purchase.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.request.api.dto.address.AddressDTO;
import com.request.api.dto.product.request.ProductPurchaseDTO;
import com.request.api.enums.MethodPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PurchaseRequestDTO {

	@Valid
	@NotNull(message = "O endereço é obrigatório")
	private AddressDTO address;

	@Size(min = 8, max = 15, message = "O campo telefone deve ter entre 8 e 15 caracteres")
	@NotNull(message = "O telefone é obrigatório")
	private String phoneNumber;

	@Valid
	@NotNull(message = "Os produto são obrigatórios")
	private List<ProductPurchaseDTO> products;
	
	@NotNull(message = "A forma de pagamento é obrigatória")
	private MethodPayment methodPayment;
	
}
