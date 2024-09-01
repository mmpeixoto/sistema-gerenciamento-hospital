package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<Paciente> listar() {
        return pacienteService.listarPacientes();
    }

    @PostMapping
    public Paciente cadastrar(@RequestBody Paciente paciente) {
        return pacienteService.inserirPaciente(paciente);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> acharPacientePorId(@PathVariable String id) {
        return pacienteService.buscarPacientePorId(id).map(paciente -> ResponseEntity.ok(paciente)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Paciente> acharPacientePorCpf(@PathVariable String cpf) {
        return pacienteService.buscarPacientePorCpf(cpf).map(paciente -> ResponseEntity.ok(paciente)).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizarPaciente(@PathVariable String id, @RequestBody Paciente paciente) {
        return ResponseEntity.ok(pacienteService.atualizarPaciente(id, paciente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable String id) {
        pacienteService.deletarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
