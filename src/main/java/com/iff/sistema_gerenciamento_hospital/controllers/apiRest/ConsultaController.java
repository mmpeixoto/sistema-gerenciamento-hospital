package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.ConsultaDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.services.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @Operation(summary = "Encontrar uma consulta por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retornado consultas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Consulta.class))})})
    @GetMapping
    public List<Consulta> listarConsultas(@Parameter(description = "Id do paciente para filtrar as consultas")
                                          @RequestParam(defaultValue = "") String pacienteId,
                                          @Parameter(description = "Id do médico para filtrar as consultas")
                                          @RequestParam(defaultValue = "") String medicoId) {
        return consultaService.listarConsultas(pacienteId, medicoId);
    }

    @Operation(summary = "Cadastrar uma nova consulta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a consulta que foi criada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Consulta.class))})})
    @PostMapping
    public Consulta cadastrarConsulta(
            @Parameter(description = "DTO da consulta a ser criada")
            @Valid @RequestBody ConsultaDto consultaDto) {
        return consultaService.inserirConsulta(consultaDto);
    }

    @Operation(summary = "Atualizar uma consulta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a consulta que foi atualizada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Consulta.class))}),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada",
                    content = @Content)})
    @PutMapping("/{id}")
    public Consulta atualizarConsulta(
            @Parameter(description = "Id da consulta a ser atualizada")
            @PathVariable String id,
            @Parameter(description = "DTO da nova versão da consulta")
            @RequestBody ConsultaDto consultaDto) {
        return consultaService.atualizarConsulta(id, consultaDto);
    }

    @Operation(summary = "Deletar uma consulta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "A consulta foi deletada com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Consulta não encontrada",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConsulta(@PathVariable String id) {
        consultaService.deletarConsulta(id);
        return ResponseEntity.noContent().build();
    }

}
