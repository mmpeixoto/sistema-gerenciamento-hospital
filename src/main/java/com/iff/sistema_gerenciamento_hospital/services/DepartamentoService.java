package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.ChefeDepartamentoDto;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.DepartamentoDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.DepartamentoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentoService {

    private final DepartamentoRepository repository;
    private final MedicoRepository medicoRepository;

    public List<Departamento> listarDepartamentos() {
        return repository.findAll();
    }

    public Departamento inserirDepartamento(DepartamentoDto departamentoDto) {
        return repository.save(DepartamentoDto.paraDepartamento(departamentoDto));
    }

    public Departamento editarChefeDepartamento(String departamentoId, ChefeDepartamentoDto chefeDepartamentoDto) {
        var departamento = repository.findById(departamentoId)
                .orElseThrow(() -> new NotFoundException("Departamento com esse Id nao encontrado"));
        var chefeDepartamento = medicoRepository.findById(chefeDepartamentoDto.getChefeDepartamentoId())
                .orElseThrow(() -> new NotFoundException("Médico com esse Id nao encontrado"));

        departamento.setChefeDeDepartamento(chefeDepartamento);
        departamento = repository.save(departamento);
        return departamento;
    }
}
