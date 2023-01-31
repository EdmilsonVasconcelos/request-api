package com.request.api.dto.product.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProductPurchaseDTO {

    @NotNull(message = "O id do produto para o pedido é obrigatório")
    private Long id;

    private Long quantity;

    ProductPurchaseDTO() {
        quantity = 1L;
    }

}
