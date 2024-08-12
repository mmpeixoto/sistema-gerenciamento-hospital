package com.iff.sistema_gerenciamento_hospital.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @GetMapping
    public String getMedicos() {
        return "Sem médicos cadastrados";
    }
}
