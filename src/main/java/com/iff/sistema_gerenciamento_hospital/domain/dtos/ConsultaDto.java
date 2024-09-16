package com.iff.sistema_gerenciamento_hospital.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class ConsultaDto {
    @NotNull(message = "Data da consulta é obrigatoria")
    private Date dataConsulta;
    private String diagnostico;
    private String tratamento;
    @NotNull(message = "Médico é obrigatorio para consulta")
    private String medicoId;
    @NotNull(message = "Triagem é obrigatoria para consulta")
    private String triagemId;
}
