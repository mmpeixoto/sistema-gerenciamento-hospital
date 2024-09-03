package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Column;
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
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;
    @Column(name = "telefone", nullable = true, length = 15)
    private String telefone;
    @Column(name = "dataNascimento", nullable = false)
    private Date dataNascimento;
    @ManyToOne
    private Endereco endereco;
}