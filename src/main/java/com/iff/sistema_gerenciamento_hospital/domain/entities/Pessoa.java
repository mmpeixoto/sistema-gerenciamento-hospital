package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
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
    private String nome;
    private String cpf;
    private String telefone;
    private Date dataNascimento;
    @ManyToOne
    private Endereco endereco;
}
