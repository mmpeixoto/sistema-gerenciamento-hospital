package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
