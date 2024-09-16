package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Data da consulta é obrigatoria")
    private Date dataConsulta;
    private String diagnostico;
    private String tratamento;
    @NotNull(message = "Médico é obrigatorio para consulta")
    @ManyToOne
    private Medico medico;
    @NotNull(message = "Triagem é obrigatoria para consulta")
    @OneToOne
    private Triagem triagem;
}
