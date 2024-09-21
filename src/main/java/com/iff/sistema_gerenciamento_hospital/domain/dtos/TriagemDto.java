package com.iff.sistema_gerenciamento_hospital.domain.dtos;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.util.Date;

@Data
public class TriagemDto {
    @NotNull(message = "Data é obrigatorio na triagem")
    @PastOrPresent(message = "A triagem nao pode ter uma data futura")
    private Date data;
    @NotBlank(message = "Paciente é obrigatorio na triagem")
    private String pacienteId;
    @NotBlank(message = "Enfermeiro é obrigatorio na triagem")
    private String enfermeiroId;
}
