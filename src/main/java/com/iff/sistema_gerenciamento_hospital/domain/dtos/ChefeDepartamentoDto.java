package com.iff.sistema_gerenciamento_hospital.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChefeDepartamentoDto {
    @NotBlank(message = "Id é obrigatório para o chefe de departamento")
    private String chefeDepartamentoId;
}
