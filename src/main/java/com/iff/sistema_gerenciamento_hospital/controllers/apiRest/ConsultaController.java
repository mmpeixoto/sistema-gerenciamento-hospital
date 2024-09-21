package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.ConsultaDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.services.ConsultaService;
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

    @GetMapping
    public List<Consulta> listarConsultas(@RequestParam(defaultValue = "") String pacienteId,
                                          @RequestParam(defaultValue = "") String medicoId) {
        return consultaService.listarConsultas(pacienteId, medicoId);
    }

    @PostMapping
    public Consulta cadastrarConsulta(@Valid @RequestBody ConsultaDto consultaDto) {
        return consultaService.inserirConsulta(consultaDto);
    }

    @PutMapping("/{id}")
    public Consulta atualizarConsulta(@PathVariable String id, @RequestBody ConsultaDto consultaDto) {
        return consultaService.atualizarConsulta(id, consultaDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConsulta(@PathVariable String id) {
        consultaService.deletarConsulta(id);
        return ResponseEntity.noContent().build();
    }

}
