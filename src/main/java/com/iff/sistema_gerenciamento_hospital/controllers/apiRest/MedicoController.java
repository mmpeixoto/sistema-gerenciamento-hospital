package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.services.MedicoService;

import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public List<Medico> listar() {
        return medicoService.listarMedicos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medico> acharMedicoPorId(@PathVariable String id) {
        return medicoService.buscarMedicoPorId(id).map(medico -> ResponseEntity.ok(medico)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/licenca/{licenca}")
    public ResponseEntity<Medico> acharMedicoPorLicenca(@PathVariable String licenca) {
        return medicoService.buscarMedicoPorLicenca(licenca).map(medico -> ResponseEntity.ok(medico)).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Medico cadastrar(@RequestBody Medico medico) {
        return medicoService.inserirMedico(medico);
    }
}
