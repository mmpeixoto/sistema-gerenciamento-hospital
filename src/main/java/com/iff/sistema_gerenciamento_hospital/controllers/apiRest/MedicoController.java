package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping
    public Medico cadastrar(@RequestBody Medico medico) {
        return medicoService.inserirMedico(medico);
    }
}
