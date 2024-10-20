package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.*;
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
    @NotNull(message = "Diagnostico é obrigatorio para consulta")
    private String diagnostico;
    @Column(name="tratamento", nullable=true, length=512)
    private String tratamento;
    @ManyToOne
    private Medico medico;
    @OneToOne
    private Triagem triagem;
    @Transient
    @NotNull(message = "Triagem é obrigatoria para consulta")
    private String triagemId;
    @Transient
    @NotNull(message = "Triagem é obrigatoria para consulta")
    private String medicoId;
}
