package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Medico extends Pessoa{
    private String especialidade;
    private String licenca;
    @ManyToOne
    private Departamento departamento;
}
