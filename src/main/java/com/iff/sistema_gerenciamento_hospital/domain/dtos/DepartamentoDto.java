package com.iff.sistema_gerenciamento_hospital.domain.dtos;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DepartamentoDto {
    @NotBlank(message = "Localização é obrigatoria para o departamento")
    private String localizacao;

    public static Departamento paraDepartamento(DepartamentoDto departamentoDto) {
        var departamento = new Departamento();
        departamento.setLocalizacao(departamentoDto.getLocalizacao());
        return departamento;
    }

}
