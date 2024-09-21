package com.iff.sistema_gerenciamento_hospital.controllers.apiRest;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.TriagemDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import com.iff.sistema_gerenciamento_hospital.services.TriagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("triagens")
@RequiredArgsConstructor
public class TriagemController {

    private final TriagemService service;

    @GetMapping
    public List<Triagem> listarTriagens() {
        return service.listarTriagens();
    }

    @PostMapping
    public Triagem inserirTriagem(@Valid @RequestBody TriagemDto triagemDto) {
        return service.inserirTriagem(triagemDto);
    }
}
