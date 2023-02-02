package com.request.api.dto.category.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Builder
@Getter
public class CategoryRequestDTO {

    private Long id;

    @NotNull(message = "O nome é obrigatório")
    @Size(message = "O nome deve ter entre 4 e 255 caracteres", min = 4, max = 255)
    private String name;
}
