package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Consulta {
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private String id;
    private Date dataConsulta;
    private String diagnostico;
    private String tratamento;
    @ManyToOne
    private Paciente paciente;
    @ManyToOne
    private Medico medico;
    @OneToOne
    private Triagem triagem;
}
