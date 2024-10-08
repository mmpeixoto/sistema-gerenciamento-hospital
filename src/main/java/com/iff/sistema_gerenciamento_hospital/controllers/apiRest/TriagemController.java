package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.TriagemDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import com.iff.sistema_gerenciamento_hospital.domain.mapper.TriagemMapper;
import com.iff.sistema_gerenciamento_hospital.services.TriagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("triagens")
@RequiredArgsConstructor
public class TriagemController {

    private final TriagemService service;
    private final TriagemMapper mapper;

    @Operation(summary = "Listar todas as triagens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de triagens",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Triagem.class))})})
    @GetMapping
    public CollectionModel<TriagemDto> listarTriagens() {
        return mapper.toCollectionModel(service.listarTriagens());
    }

    @Operation(summary = "Listar uma triagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a triagem especificada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Triagem.class))}),
            @ApiResponse(responseCode = "404", description = "Triagem nao encontrada",
                    content = @Content)})
    @GetMapping("/{id}")
    public TriagemDto getTriagem(@PathParam("id") String id) {
        return mapper.toModel(service.getTriagem(id));
    }

    @Operation(summary = "Inserir uma nova triagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a triagem que foi criada",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Triagem.class))})})
    @PostMapping
    public TriagemDto inserirTriagem(
            @Parameter(description = "DTO da triagem a ser criada")
            @Valid @RequestBody TriagemDto triagemDto) {
        return mapper.toModel(service.inserirTriagem(triagemDto));
    }
}