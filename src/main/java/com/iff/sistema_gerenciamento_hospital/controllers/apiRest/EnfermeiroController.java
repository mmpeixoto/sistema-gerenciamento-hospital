package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.EnfermeiroDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import com.iff.sistema_gerenciamento_hospital.domain.mapper.EnfermeiroMapper;
import com.iff.sistema_gerenciamento_hospital.services.EnfermeiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("enfermeiros")
@RequiredArgsConstructor
public class EnfermeiroController {

    private final EnfermeiroService enfermeiroService;

    private final EnfermeiroMapper enfermeiroMapper;

    @Operation(summary = "Listar todos os enfermeiros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de enfermeiros",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Enfermeiro.class))})})
    @GetMapping
    public CollectionModel<EnfermeiroDto> listarEnfermeiros() {
        return enfermeiroMapper.toCollectionModel(enfermeiroService.listarEnfermeiros());
    }

    @Operation(summary = "Buscar enfermeiro por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o enfermeiro encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Enfermeiro.class))}),
            @ApiResponse(responseCode = "404", description = "Enfermeiro não encontrado",
                    content = @Content)})
    @GetMapping("/{id}")
    public EnfermeiroDto buscarEnfermeiroPorId(
            @Parameter(description = "Id do enfermeiro a ser buscado")
            @PathVariable String id) {
        return enfermeiroMapper.toModel(enfermeiroService.buscarEnfermeiroPorId(id));
    }

    @Operation(summary = "Cadastrar um novo enfermeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o enfermeiro que foi criado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Enfermeiro.class))})})
    @PostMapping
    public EnfermeiroDto cadastrarEnfermeiro(
            @Parameter(description = "DTO do enfermeiro a ser criado")
            @Valid @RequestBody EnfermeiroDto enfermeiroDto) {
        return enfermeiroMapper.toModel(enfermeiroService.inserirEnfermeiro(enfermeiroDto));
    }

    @Operation(summary = "Atualizar um enfermeiro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o enfermeiro que foi atualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Enfermeiro.class))}),
            @ApiResponse(responseCode = "404", description = "Enfermeiro não encontrado",
                    content = @Content)})
    @PutMapping("/{id}")
    public EnfermeiroDto atualizarEnfermeiro(
            @Parameter(description = "Id do enfermeiro a ser atualizado")
            @PathVariable String id,
            @Parameter(description = "Nova versão do enfermeiro")
            @RequestBody EnfermeiroDto enfermeiroDto) {
        return enfermeiroMapper.toModel(enfermeiroService.atualizarEnfermeiro(id, enfermeiroDto));
    }

    @Operation(summary = "Deletar um enfermeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "O enfermeiro foi deletado com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Enfermeiro não encontrado",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEnfermeiro(
            @Parameter(description = "Id do enfermeiro a ser deletado")
            @PathVariable String id) {
        enfermeiroService.deletarEnfermeiro(id);
        return ResponseEntity.noContent().build();
    }

}