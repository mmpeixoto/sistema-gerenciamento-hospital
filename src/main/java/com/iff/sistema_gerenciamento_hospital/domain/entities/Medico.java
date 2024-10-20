package com.iff.sistema_gerenciamento_hospital.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Medico extends Pessoa{
    @NotBlank(message = "Especialidade é obrigatorio no medico")
    @Column(name = "especialidade", nullable = false, length = 20)
    private String especialidade;
    @Column(name = "licenca", nullable = false, length = 15)
    @NotBlank(message = "Licença é obrigatorio no medico")
    private String licenca;
    @ManyToOne
    @NotNull(message = "Departamento é obrigatorio no medico")
    @JsonIgnoreProperties({"enfermeiros", "medicos"})
    private Departamento departamento;
    @Transient
    private String departamentoId;
}
