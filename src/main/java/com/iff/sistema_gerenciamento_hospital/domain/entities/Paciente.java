package com.iff.sistema_gerenciamento_hospital.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
    @OneToMany(mappedBy = "paciente")
    @JsonIgnoreProperties("paciente")
    private List<Exame> exames;
}
