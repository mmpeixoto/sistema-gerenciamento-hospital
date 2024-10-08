package com.iff.sistema_gerenciamento_hospital.domain.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ConsultaDto extends RepresentationModel<ConsultaDto> {
    private String id;
    @NotNull(message = "Data da consulta é obrigatoria")
    private Date dataConsulta;
    private String diagnostico;
    private String tratamento;
    @NotNull(message = "Médico é obrigatorio para consulta")
    private MedicoDto medico;
    @NotNull(message = "Triagem é obrigatoria para consulta")
    private TriagemDto triagem;
}
