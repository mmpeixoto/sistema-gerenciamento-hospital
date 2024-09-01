package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Medico extends Pessoa{
    @Column(name = "especialidade", nullable = true, length = 20)
    private String especialidade;
    @Column(name = "licenca", nullable = true, length = 15)
    private String licenca;
    @ManyToOne
    private Departamento departamento;
}
