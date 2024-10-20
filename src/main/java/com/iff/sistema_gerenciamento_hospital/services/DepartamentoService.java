package com.iff.sistema_gerenciamento_hospital.services;

import ch.qos.logback.core.util.StringUtil;
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

    public Departamento inserirDepartamento(Departamento departamento) {
        if (!StringUtil.isNullOrEmpty(departamento.getChefeDeDepartamentoId())) {
            var chefeDpto = medicoRepository.findById(departamento.getChefeDeDepartamentoId())
                    .orElseThrow(() -> new NotFoundException("Médico com esse Id nao encontrado"));
            departamento.setChefeDeDepartamento(chefeDpto);
        }
        return repository.save(departamento);
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

    public Departamento getDepartamento(String departamentoId) {
        return repository.findById(departamentoId)
                .orElseThrow(() -> new NotFoundException("Departamento com esse Id nao encontrado"));
    }

    public void deletarDepartamento(String departamentoId) {
        var departamento = repository.findById(departamentoId)
                .orElseThrow(() -> new NotFoundException("Departamento com esse Id nao encontrado"));
        repository.delete(departamento);
    }

    public Departamento editarDepartamento(String idDepartamento, Departamento departamento) {
        var departamentoAtual = repository.findById(idDepartamento)
                .orElseThrow(() -> new NotFoundException("Departamento com esse Id nao encontrado"));

        if (!StringUtil.isNullOrEmpty(departamento.getChefeDeDepartamentoId())) {
            var chefeDpto = medicoRepository.findById(departamento.getChefeDeDepartamentoId())
                    .orElseThrow(() -> new NotFoundException("Médico com esse Id nao encontrado"));
            departamento.setChefeDeDepartamento(chefeDpto);
        }

        departamento.setId(departamentoAtual.getId());
        return repository.save(departamento);
    }
}
