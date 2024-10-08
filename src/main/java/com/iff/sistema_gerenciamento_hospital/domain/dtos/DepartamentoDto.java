package com.iff.sistema_gerenciamento_hospital.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DepartamentoDto extends RepresentationModel<DepartamentoDto> {
    private String id;
    @NotBlank(message = "Localização é obrigatoria para o departamento")
    private String localizacao;
    private CollectionModel<MedicoDto> medicos;
    private CollectionModel<EnfermeiroDto> enfermeiros;

    public static Departamento paraDepartamento(DepartamentoDto departamentoDto) {
        var departamento = new Departamento();
        departamento.setLocalizacao(departamentoDto.getLocalizacao());
        return departamento;
    }

}
