package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        Endereco endereco = paciente.getEndereco();
        if (endereco == null) {
            throw new BadRequestException("Erro: O endereço é necessário para o cadastro do cliente");
        }

        Optional<Endereco> enderecoExistente = enderecoRepository.acharPorCepLogradouroNumeroComplemento(
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento()
        );

        if (enderecoExistente.isPresent()) {
            paciente.setEndereco(enderecoExistente.get());
        } else{
            enderecoRepository.save(paciente.getEndereco());
            paciente.setEndereco(endereco);
        }

        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPacientePorId(String id) {
        return pacienteRepository.findById(id);
    }

    public Optional<Paciente> buscarPacientePorCpf(String cpf) {
        return pacienteRepository.acharPorCpf(cpf);
    }

    public Paciente atualizarPaciente(String id, Paciente paciente) {
        Paciente pacienteExistente = pacienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Paciente não encontrado!"));
        if (pacienteRepository.acharPorCpf(paciente.getCpf()).isPresent() && !pacienteExistente.getCpf().equals(paciente.getCpf())) {
            throw new BadRequestException("Erro: CPF do cliente ja cadastrado!");
        }
        paciente.setId(id);
        return inserirPaciente(paciente);
    }

    public void deletarPaciente(String id) {
        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id);
        } else {
            throw new NotFoundException("Paciente não encontrado!");
        }
    }

}
