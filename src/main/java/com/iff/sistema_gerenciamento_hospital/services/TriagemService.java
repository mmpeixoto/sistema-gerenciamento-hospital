package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.TriagemDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.EnfermeiroRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.PacienteRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.TriagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TriagemService {

    private final TriagemRepository repository;
    private final EnfermeiroRepository enfermeiroRepository;
    private final PacienteRepository pacienteRepository;

    public List<Triagem> listarTriagens() {
        return repository.findAll();
    }

    public Triagem getTriagem(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Triagem não encontrada!"));
    }

    public Triagem inserirTriagem(TriagemDto triagemDto) {
        return repository.save(paraTriagem(triagemDto));
    }

    public Triagem inserirTriagem(Triagem triagem) {
        var enfermeiro = enfermeiroRepository.findById(triagem.getEnfermeiroId())
                .orElseThrow(() -> new NotFoundException("Enfermeiro não encontrado"));
        var paciente = pacienteRepository.findById(triagem.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

        triagem.setEnfermeiro(enfermeiro);
        triagem.setPaciente(paciente);
        return repository.save(triagem);
    }

    public Triagem atualizarTriagem(String id, Triagem triagem) {
        var triagemExistente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Triagem não encontrada!"));
        var enfermeiro = enfermeiroRepository.findById(triagem.getEnfermeiroId())
                        .orElseThrow(() -> new NotFoundException("Enfermeiro não encontrado"));
        var paciente = pacienteRepository.findById(triagem.getPacienteId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

        triagem.setId(triagemExistente.getId());
        triagem.setEnfermeiro(enfermeiro);
        triagem.setPaciente(paciente);
        return repository.save(triagem);
    }

    public void deleteTriagem(String id) {
        repository.deleteById(id);
    }

    private Triagem paraTriagem(TriagemDto triagemDto) {
        var enfermeiro = enfermeiroRepository.findById(triagemDto.getEnfermeiro().getId())
                .orElseThrow(() -> new NotFoundException("Enfermeiro não encontrado"));
        var paciente = pacienteRepository.findById(triagemDto.getPaciente().getId())
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

        var triagem = new Triagem();
        triagem.setData(triagemDto.getData());
        triagem.setEnfermeiro(enfermeiro);
        triagem.setPaciente(paciente);
        return triagem;
    }
}
