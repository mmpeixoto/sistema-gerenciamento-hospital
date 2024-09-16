package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.DepartamentoDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.repositories.DepartamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepository repository;

    public List<Departamento> listarDepartamentos() {
        return repository.findAll();
    }

    public Departamento inserirDepartamento(DepartamentoDto departamentoDto) {
        return repository.save(DepartamentoDto.paraDepartamento(departamentoDto));
    }
}
