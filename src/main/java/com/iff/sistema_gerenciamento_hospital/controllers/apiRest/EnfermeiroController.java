package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.EnfermeiroDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import com.iff.sistema_gerenciamento_hospital.services.EnfermeiroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("enfermeiros")
@RequiredArgsConstructor
public class EnfermeiroController {

    private final EnfermeiroService service;

    @GetMapping
    public List<Enfermeiro> listarEnfermeiros() {
        return service.listarEnfermeiros();
    }

    @PostMapping
    public Enfermeiro inserirEnfermeiro(@Valid @RequestBody EnfermeiroDto enfermeiroDto) {
        return service.inserirEnfermeiro(enfermeiroDto);
    }
}
