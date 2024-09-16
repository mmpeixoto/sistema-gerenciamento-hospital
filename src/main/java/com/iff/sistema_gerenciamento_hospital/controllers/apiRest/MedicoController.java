package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.services.MedicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping
    public List<Medico> listarMedicos() {
        return medicoService.listarMedicos();
    }

    @GetMapping("/{id}")
    public Medico buscarMedicoPorId(@PathVariable String id) {
        return medicoService.buscarMedicoPorId(id);
    }

    @GetMapping("/licenca/{licenca}")
    public Medico buscarMedicoPorLicenca(@PathVariable String licenca) {
        return medicoService.buscarMedicoPorLicenca(licenca);
    }

    @PostMapping
    public Medico cadastrar(@RequestBody Medico medico) {
        return medicoService.inserirMedico(medico);
    }

    @PutMapping("/{id}")
    public Medico atualizarMedico(@PathVariable String id, @RequestBody Medico medico) throws IllegalAccessException {
        return medicoService.atualizarMedico(id, medico);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMedico(@PathVariable String id) {
        medicoService.deletarMedico(id);
        return ResponseEntity.noContent().build();
    }
}
