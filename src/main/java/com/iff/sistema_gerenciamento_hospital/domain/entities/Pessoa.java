package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Data
@MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pessoa {
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private String id;
    @NotBlank(message = "Nome é obrigatorio na pessoa")
    private String nome;
    @NotBlank(message = "CPF é obrigatorio na pessoa")
    @Column(unique = true)
    private String cpf;
    private String telefone;
    @NotNull(message = "Data de nascimento é obrigatorio na pessoa")
    @PastOrPresent(message = "A data de nascimento nao pode estar no futuro")
    private Date dataNascimento;
    @ManyToOne
    private Endereco endereco;
}
