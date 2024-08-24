package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
    private Date data;
    @ManyToOne
    private Paciente paciente;
    @ManyToOne
    private Enfermeiro enfermeiro;
}
