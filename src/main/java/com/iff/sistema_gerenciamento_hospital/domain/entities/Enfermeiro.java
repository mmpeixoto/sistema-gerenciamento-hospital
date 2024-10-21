package com.iff.sistema_gerenciamento_hospital.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Enfermeiro extends Pessoa {

    @ManyToOne
    @NotNull(message = "O departamento é obrigatorio")
    @JsonIgnoreProperties({"enfermeiros", "medicos"})
    private Departamento departamento;

    @Transient
    private String departamentoId;

    public void setDepartamentoId(String departamentoId) {
        this.departamentoId = departamentoId;
        if (departamentoId != null) {
            this.departamento = new Departamento();
            this.departamento.setId(departamentoId);
        }
    }
}
