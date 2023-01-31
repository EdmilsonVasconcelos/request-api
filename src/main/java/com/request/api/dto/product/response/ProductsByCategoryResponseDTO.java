package com.request.api.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductsByCategoryResponseDTO {

    private String category;

    private List<ProductResponseDTO> products;
}
