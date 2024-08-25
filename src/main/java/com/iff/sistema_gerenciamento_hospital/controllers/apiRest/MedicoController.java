package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Medico cadastrar(@RequestBody Medico medico) {return medicoService.inserirMedico(medico);}
}
