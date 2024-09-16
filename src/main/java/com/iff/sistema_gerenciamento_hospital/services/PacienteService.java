package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final EnderecoRepository enderecoRepository;

    public Paciente inserirPaciente(Paciente paciente) {
        if (paciente.getCpf() == null) {
            throw new BadRequestException("Erro: O CPF é necessário para o cadastro do paciente");
        }

        verificarCpfExistente(paciente.getCpf());

        Endereco endereco = verificarOuSalvarEndereco(paciente.getEndereco());

        paciente.setEndereco(endereco);
        return pacienteRepository.save(paciente);
    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente buscarPacientePorId(String id) {
        return pacienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Paciente não encontrado!"));
    }

    public Paciente buscarPacientePorCpf(String cpf) {
        return pacienteRepository.acharPorCpf(cpf).orElseThrow(() -> new NotFoundException("Paciente não encontrado!"));
    }

    public Paciente atualizarPaciente(String id, Paciente paciente) {
        Paciente pacienteExistente = pacienteRepository.findById(id).orElseThrow(() -> new NotFoundException("Paciente não encontrado!"));
        if (!pacienteExistente.getCpf().equals(paciente.getCpf())) {
            verificarCpfExistente(paciente.getCpf());
        }

        Endereco endereco = verificarOuSalvarEndereco(paciente.getEndereco());

        paciente.setId(id);
        paciente.setEndereco(endereco);

        return pacienteRepository.save(paciente);
    }

    public void deletarPaciente(String id) {
        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id);
        } else {
            throw new NotFoundException("Paciente não encontrado!");
        }
    }

    private void verificarCpfExistente(String cpf) {
        if (pacienteRepository.acharPorCpf(cpf).isPresent()) {
            throw new BadRequestException("Erro: CPF do cliente já cadastrado!");
        }
    }

    private Endereco verificarOuSalvarEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new BadRequestException("Erro: O endereço é necessário para o cadastro do cliente");
        }

        return enderecoRepository.acharPorCepLogradouroNumeroComplemento(
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento()
        ).orElseGet(() -> enderecoRepository.save(endereco));
    }

}
