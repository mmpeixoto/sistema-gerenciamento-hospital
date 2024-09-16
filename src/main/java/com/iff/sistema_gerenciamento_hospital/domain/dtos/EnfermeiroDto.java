package com.iff.sistema_gerenciamento_hospital.domain.dtos;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.util.Date;

@Data
public class EnfermeiroDto {
    @NotBlank(message = "Nome é obrigatorio para o enfermeiro")
    private String nome;
    @Column(unique = true)
    private String cpf;
    private String telefone;
    @NotNull(message = "Data de nascimento é obrigatorio na pessoa")
    @PastOrPresent(message = "A data de nascimento nao pode estar no futuro")
    private Date dataNascimento;
    private Endereco endereco;
}