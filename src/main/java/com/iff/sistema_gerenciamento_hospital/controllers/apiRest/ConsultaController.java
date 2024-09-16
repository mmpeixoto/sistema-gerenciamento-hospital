package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.services.ConsultaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @GetMapping
    public List<Consulta> listarConsultas() {
        return consultaService.listarConsultas();
    }

    @GetMapping("paciente/{pacienteId}")
    public List<Consulta> listarConsultasPorPaciente(@PathVariable String pacienteId) {
        return consultaService.listarConsultasPaciente(pacienteId);
    }

    @GetMapping("medico/{medicoId}")
    public List<Consulta> listarConsultasPorMedico(@PathVariable String medicoId) {
        return consultaService.listarConsultasMedico(medicoId);
    }

    @PostMapping
    public Consulta cadastrarConsulta(@RequestBody Consulta consulta) {
        return consultaService.inserirConsulta(consulta);
    }

    @PutMapping("/{id}")
    public Consulta atualizarConsulta(@PathVariable String id, @RequestBody Consulta consulta) {
        return consultaService.atualizarConsulta(id, consulta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConsulta(@PathVariable String id) {
        consultaService.deletarConsulta(id);
        return ResponseEntity.noContent().build();
    }

}
