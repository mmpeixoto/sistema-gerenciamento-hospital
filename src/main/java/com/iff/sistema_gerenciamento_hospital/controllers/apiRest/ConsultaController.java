package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.services.ConsultaService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public List<Consulta> consultas() {
        return consultaService.listarConsultas();
    }

    @GetMapping("paciente/{pacienteId}")
    public List<Consulta> consultasPorPaciente(@PathVariable String pacienteId) {
        return consultaService.listarConsultasPaciente(pacienteId);
    }

    @GetMapping("medico/{medicoId}")
    public List<Consulta> consultasPorMedico(@PathVariable String medicoId) {
        return consultaService.listarConsultasMedico(medicoId);
    }

    @PostMapping
    public ResponseEntity<Consulta> cadastrar(@RequestBody Consulta consulta) {
        return ResponseEntity.ok(consultaService.inserirConsulta(consulta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consulta> atualizarConsulta(@PathVariable String id, @RequestBody Consulta consulta) {
        return ResponseEntity.ok(consultaService.atualizarConsulta(id, consulta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConsulta(@PathVariable String id) {
        consultaService.deletarConsulta(id);
        return ResponseEntity.noContent().build();
    }

}
