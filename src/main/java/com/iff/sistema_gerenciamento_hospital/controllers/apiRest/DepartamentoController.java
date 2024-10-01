package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.ChefeDepartamentoDto;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.DepartamentoDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.services.DepartamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos/")
@RequiredArgsConstructor
public class DepartamentoController {

    private final DepartamentoService service;

    @Operation(summary = "Listar todos os departamentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de departamentos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Departamento.class))})})
    @GetMapping
    public List<Departamento> listarDepartamentos() {
        return service.listarDepartamentos();
    }

    @Operation(summary = "Inserir um novo departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o departamento que foi criado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Departamento.class))})})
    @PostMapping
    public Departamento inserirDepartamento(
            @Parameter(description = "DTO do departamento a ser criado")
            @Valid @RequestBody DepartamentoDto departamentoDto) {
        return service.inserirDepartamento(departamentoDto);
    }

    @Operation(summary = "Editar o chefe de um departamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o departamento com o chefe atualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Departamento.class))}),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado",
                    content = @Content)})
    @PutMapping("/{departamentoId}/chefe")
    public Departamento editarChefeDepartamento(
            @Parameter(description = "Id do departamento a ser atualizado")
            @PathParam("departamentoId") String departamentoId,
            @Parameter(description = "DTO do novo chefe do departamento")
            @RequestBody @Valid ChefeDepartamentoDto chefeDepartamentoDto) {
        return service.editarChefeDepartamento(departamentoId, chefeDepartamentoDto);
    }
}