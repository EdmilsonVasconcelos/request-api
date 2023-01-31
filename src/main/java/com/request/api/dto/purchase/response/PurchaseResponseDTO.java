package com.request.api.dto.purchase.response;

import java.math.BigDecimal;
import java.util.List;


import com.request.api.dto.customer.response.CustomerResponseDTO;
import com.request.api.dto.product.response.ProductResponseDTO;
import com.request.api.enums.MethodPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponseDTO {
	
	private Long id;
	
	private CustomerResponseDTO customer;
	
	private List<ProductResponseDTO> products;
	
	private String observation;
	
	private MethodPayment methodPayment;
	
	private BigDecimal value;
	
	private Boolean isOpen;

}
