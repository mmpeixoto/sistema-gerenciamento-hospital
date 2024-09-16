package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Column;
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
    @Column(name="dataConsulta", nullable=false)
    private Date dataConsulta;
    @Column(name="diagnostico", nullable=false, length = 512)
    private String diagnostico;
    @Column(name="tratamento", nullable=true, length=512)
    private String tratamento;
    @NotNull(message = "Médico é obrigatorio para consulta")
    @ManyToOne
    private Medico medico;
    @NotNull(message = "Triagem é obrigatoria para consulta")
    @OneToOne
    private Triagem triagem;
}
