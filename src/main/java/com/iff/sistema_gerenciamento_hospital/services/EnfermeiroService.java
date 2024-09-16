package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.EnfermeiroDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import com.iff.sistema_gerenciamento_hospital.repositories.EnfermeiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnfermeiroService {

    private final EnfermeiroRepository repository;

    public Enfermeiro inserirEnfermeiro(EnfermeiroDto novoEnfermeiro) {

    }
}
