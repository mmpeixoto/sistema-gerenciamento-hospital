package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.services.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    public List<Paciente> listar() {
        return pacienteService.listarPacientes();
    }

    @PostMapping
    public Paciente cadastrar(@RequestBody Paciente paciente) {
        return pacienteService.inserirPaciente(paciente);
    }

    @GetMapping("/{id}")
    public Paciente acharPacientePorId(@PathVariable String id) {
        return pacienteService.buscarPacientePorId(id);
    }

    @GetMapping("/cpf/{cpf}")
    public Paciente acharPacientePorCpf(@PathVariable String cpf) {
        return pacienteService.buscarPacientePorCpf(cpf);
    }

    @PutMapping("/{id}")
    public Paciente atualizarPaciente(@PathVariable String id, @RequestBody Paciente paciente) {
        return pacienteService.atualizarPaciente(id, paciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPaciente(@PathVariable String id) {
        pacienteService.deletarPaciente(id);
        return ResponseEntity.noContent().build();
    }
}
