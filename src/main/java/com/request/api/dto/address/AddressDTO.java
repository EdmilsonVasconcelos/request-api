package com.request.api.dto.address;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddressDTO {

    @Size(min = 2, max = 255, message = "A rua deve ter entre 2 e 255 caracteres")
    @NotNull(message = "A rua é obrigatória")
    private String street;

    @Size(min = 2, max = 255, message = "O número deve ter entre 2 e 255 caracteres")
    @NotNull(message = "A número da casa é obrigatório")
    private String number;

    @Size(min = 2, max = 255, message = "O bairro deve ter entre 2 e 255 caracteres")
    @NotNull(message = "A bairro é obrigatória")
    private String neighborhood;

}
