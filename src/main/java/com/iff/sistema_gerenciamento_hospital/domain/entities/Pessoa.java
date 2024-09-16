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
    @Column(name = "nome", nullable = false, length = 50, unique = true)
    private String nome;
    @NotBlank(message = "CPF é obrigatorio na pessoa")
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;
    @Column(name = "telefone", nullable = true, length = 15)
    private String telefone;
    @NotNull(message = "Data de nascimento é obrigatorio na pessoa")
    @PastOrPresent(message = "A data de nascimento nao pode estar no futuro")
    @Column(name = "dataNascimento", nullable = false)
    private Date dataNascimento;
    @ManyToOne
    private Endereco endereco;
}