package com.iff.sistema_gerenciamento_hospital.domain.dtos;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
public class PacienteDto extends RepresentationModel<PacienteDto> {
    private String id;
    @NotBlank(message = "Nome é obrigatorio para o paciente")
    private String nome;
    private String cpf;
    private String telefone;
    private Endereco endereco;
    private Date dataNascimento;
    private float peso;
    private float altura;
    private String tipoSanguineo;
}