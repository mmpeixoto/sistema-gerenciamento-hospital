package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.MedicoDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.mapper.MedicoMapper;
import com.iff.sistema_gerenciamento_hospital.services.MedicoService;
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
@RequestMapping("medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    private final MedicoMapper medicoMapper;

    @Operation(summary = "Listar todos os médicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de médicos",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Medico.class))})})
    @GetMapping
    public CollectionModel<MedicoDto> listarMedicos() {
        return medicoMapper.toCollectionModel(medicoService.listarMedicos());
    }

    @Operation(summary = "Buscar médico por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o médico encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Medico.class))}),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado",
                    content = @Content)})
    @GetMapping("/{id}")
    public MedicoDto buscarMedicoPorId(
            @Parameter(description = "Id do médico a ser buscado")
            @PathVariable String id) {
        return medicoMapper.toModel(medicoService.buscarMedicoPorId(id));
    }

    @Operation(summary = "Buscar médico por licença")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o médico encontrado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Medico.class))}),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado",
                    content = @Content)})
    @GetMapping("/licenca/{licenca}")
    public MedicoDto buscarMedicoPorLicenca(
            @Parameter(description = "Licença do médico a ser buscado")
            @PathVariable String licenca) {
        return medicoMapper.toModel(medicoService.buscarMedicoPorLicenca(licenca));
    }

    @Operation(summary = "Cadastrar um novo médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o médico que foi criado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Medico.class))})})
    @PostMapping
    public Medico cadastrar(
            @Parameter(description = "DTO do médico a ser criado")
            @Valid @RequestBody MedicoDto medicoDto) {
        return medicoService.inserirMedico(medicoDto);
    }

    @Operation(summary = "Atualizar um médico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o médico que foi atualizado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Medico.class))}),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado",
                    content = @Content)})
    @PutMapping("/{id}")
    public Medico atualizarMedico(
            @Parameter(description = "Id do médico a ser atualizado")
            @PathVariable String id,
            @Parameter(description = "Nova versão do médico")
            @RequestBody Medico medico) throws IllegalAccessException {
        return medicoService.atualizarMedico(id, medico);
    }

    @Operation(summary = "Deletar um médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "O médico foi deletado com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Médico não encontrado",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMedico(
            @Parameter(description = "Id do médico a ser deletado")
            @PathVariable String id) {
        medicoService.deletarMedico(id);
        return ResponseEntity.noContent().build();
    }
}