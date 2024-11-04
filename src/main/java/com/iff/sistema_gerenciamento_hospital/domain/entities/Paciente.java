package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Paciente extends Pessoa {
    @Column(name = "peso", nullable = true)
    private float peso;
    @Column(name = "altura", nullable = true)
    private float altura;
    @Column(name = "tipoSanguineo", nullable = true, length = 3)
    private String tipoSanguineo;
}
