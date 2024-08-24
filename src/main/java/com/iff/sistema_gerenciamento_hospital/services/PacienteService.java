package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Paciente inserirPaciente(Paciente paciente) {
        if (pacienteRepository.acharPorCpf(paciente.getCpf()).isPresent()) {
            throw new BadRequestException("Erro: CPF do cliente ja cadastrado!");
        }

        if (paciente.getEndereco() == null) {
            throw new BadRequestException("Erro: O endereço é necessário para o cadastro do cliente");
        }

        enderecoRepository.save(paciente.getEndereco());
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }
}
