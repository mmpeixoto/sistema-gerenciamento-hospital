package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.EnfermeiroDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import com.iff.sistema_gerenciamento_hospital.services.EnfermeiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("enfermeiros")
@RequiredArgsConstructor
public class EnfermeiroController {

    private final EnfermeiroService service;

    @Operation(summary = "Listar todos os enfermeiros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de enfermeiros",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Enfermeiro.class))})})
    @GetMapping
    public List<Enfermeiro> listarEnfermeiros() {
        return service.listarEnfermeiros();
    }

    @Operation(summary = "Inserir um novo enfermeiro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o enfermeiro que foi criado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Enfermeiro.class))})})
    @PostMapping
    public Enfermeiro inserirEnfermeiro(
            @Parameter(description = "DTO do enfermeiro a ser criado")
            @Valid @RequestBody EnfermeiroDto enfermeiroDto) {
        return service.inserirEnfermeiro(enfermeiroDto);
    }
}