package com.iff.sistema_gerenciamento_hospital.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TriagemDto extends RepresentationModel<TriagemDto> {
    private String id;
    @NotNull(message = "Data é obrigatorio na triagem")
    @PastOrPresent(message = "A triagem nao pode ter uma data futura")
    private Date data;
    @NotNull(message = "Paciente é obrigatorio na triagem")
    private PacienteDto paciente;
    @NotNull(message = "Enfermeiro é obrigatorio na triagem")
    private EnfermeiroDto enfermeiro;
}
