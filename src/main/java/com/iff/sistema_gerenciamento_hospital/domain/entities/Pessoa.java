package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Pessoa {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String nome;
    private String cpf;
    private String telefone;
    private Date dataNascimento;
}
