package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.PacienteDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.domain.mapper.PacienteMapper;
import com.iff.sistema_gerenciamento_hospital.services.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    private final PacienteMapper pacienteMapper;

    @Operation(summary = "Listar todos os pacientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de pacientes",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Paciente.class))})})
    @GetMapping
    public CollectionModel<PacienteDto> listarPacientes() {
        return pacienteMapper.toCollectionModel(pacienteService.listarPacientes());
    }

    @Operation(summary = "Cadastrar um novo paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o paciente que foi criado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Paciente.class))})})
    @PostMapping
    public Paciente cadastrarPaciente(
            @Parameter(description = "Dados do paciente a ser criado")
            @RequestBody Paciente paciente) {
        return pacienteService.inserirPaciente(paciente);
    }

    @Operation(summary = "Buscar paciente por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o paciente encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Paciente.class))}),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content)})
    @GetMapping("/{id}")
    public PacienteDto acharPacientePorId(
            @Parameter(description = "Id do paciente a ser buscado")
            @PathVariable String id) {
        return pacienteMapper.toModel(pacienteService.buscarPacientePorId(id));
    }

    @Operation(summary = "Buscar paciente por CPF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o paciente encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Paciente.class))}),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Endereço invalido ou CPF já existente",
                    content = @Content)})
    @GetMapping("/cpf/{cpf}")
    public PacienteDto acharPacientePorCpf(
            @Parameter(description = "CPF do paciente a ser buscado")
            @PathVariable String cpf) {
        return pacienteMapper.toModel(pacienteService.buscarPacientePorCpf(cpf));
    }

    @Operation(summary = "Atualizar um paciente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o paciente que foi atualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Paciente.class))}),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content)})
    @PutMapping("/{id}")
    public Paciente atualizarPaciente(
            @Parameter(description = "Id do paciente a ser atualizado")
            @PathVariable String id,
            @Parameter(description = "Nova versão do paciente")
            @RequestBody Paciente paciente) {
        return pacienteService.atualizarPaciente(id, paciente);
    }

    @Operation(summary = "Deletar um paciente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "O paciente foi deletado com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Paciente não encontrado",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(
            @Parameter(description = "Id do paciente a ser deletado")
            @PathVariable String id) {
        pacienteService.deletarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}