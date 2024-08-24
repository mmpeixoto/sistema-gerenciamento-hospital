package com.iff.sistema_gerenciamento_hospital.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Paciente extends Pessoa{
    private float peso;
    private float altura;
    private String tipoSanguineo;
    @OneToMany(mappedBy = "paciente")
    @JsonIgnoreProperties("paciente")
    private List<Exame> exames;
}
