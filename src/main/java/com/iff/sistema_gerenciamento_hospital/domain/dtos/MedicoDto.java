package com.iff.sistema_gerenciamento_hospital.domain.dtos;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.util.Date;

@Data
public class MedicoDto {
    @NotBlank(message = "Nome é obrigatorio para o enfermeiro")
    private String nome;
    @NotBlank(message = "CPF é obrigatorio para enfermeiro")
    private String cpf;
    private String telefone;
    @NotNull(message = "Data de nascimento é obrigatorio na pessoa")
    @PastOrPresent(message = "A data de nascimento nao pode estar no futuro")
    private Date dataNascimento;
    private Endereco endereco;
    @NotBlank(message = "Departamento é obrigatorio para enfermeiro")
    private String departamentoId;
    @NotBlank(message = "Especialidade é obrigatorio no medico")
    private String especialidade;
    @NotBlank(message = "Licença é obrigatorio no medico")
    private String licenca;
}
