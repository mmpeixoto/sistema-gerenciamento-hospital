package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Triagem {
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private String id;
    @NotNull(message = "Data é obrigatorio na triagem")
    @PastOrPresent(message = "A triagem nao pode ter uma data futura")
    @Column(name="data", nullable=false)
    private Date data;
    @ManyToOne
    @NotNull(message = "Paciente é obrigatorio na triagem")
    private Paciente paciente;
    @ManyToOne
    @NotNull(message = "Enfermeiro é obrigatorio na triagem")
    private Enfermeiro enfermeiro;
}
