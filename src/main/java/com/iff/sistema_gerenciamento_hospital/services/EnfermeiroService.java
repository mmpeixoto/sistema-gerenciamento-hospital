package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.EnfermeiroDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.DepartamentoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.EnfermeiroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnfermeiroService {

    private final EnfermeiroRepository repository;
    private final DepartamentoRepository departamentoRepository;
    private final EnderecoRepository enderecoRepository;
    private final EnfermeiroRepository enfermeiroRepository;

    public List<Enfermeiro> listarEnfermeiros() {
        return repository.findAll();
    }

    public Enfermeiro inserirEnfermeiro(EnfermeiroDto novoEnfermeiro) {
        return repository.save(paraEnfermeiro(novoEnfermeiro));
    }

    public Enfermeiro inserirEnfermeiro(Enfermeiro novoEnfermeiro) {
        var departamento = departamentoRepository.findById(novoEnfermeiro.getDepartamentoId())
                .orElseThrow(() -> new NotFoundException("Departamento nao encontrado"));
        var endereco = verificarOuSalvarEndereco(novoEnfermeiro.getEndereco());
        novoEnfermeiro.setEndereco(endereco);
        novoEnfermeiro.setDepartamento(departamento);
        return repository.save(novoEnfermeiro);
    }

    public Enfermeiro buscarEnfermeiroPorId(String id) {
        return enfermeiroRepository.findById(id).orElseThrow(() -> new NotFoundException("Enfermeiro não encontrado!"));
    }

    private Endereco verificarOuSalvarEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new BadRequestException("Erro: O endereço é necessário para o cadastro do médico");
        }

        return enderecoRepository.acharPorCepLogradouroNumeroComplemento(
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento()
        ).orElseGet(() -> enderecoRepository.save(endereco));
    }

    public Enfermeiro atualizarEnfermeiro(String id, EnfermeiroDto enfermeiroDto) {
        Enfermeiro enfermeiroExistente = enfermeiroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enfermeiro não encontrado!"));

        if (!enfermeiroExistente.getCpf().equals(enfermeiroDto.getCpf())) {
            verificarCpfExistente(enfermeiroDto.getCpf());
        }

        return enfermeiroRepository.save(paraEnfermeiro(enfermeiroDto));
    }

    public Enfermeiro atualizarEnfermeiro(String id, Enfermeiro enfermeiro) {
        Enfermeiro enfermeiroExistente = enfermeiroRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enfermeiro não encontrado!"));

        if (!enfermeiroExistente.getCpf().equals(enfermeiro.getCpf())) {
            verificarCpfExistente(enfermeiro.getCpf());
        }

        var departamento = departamentoRepository.findById(enfermeiro.getDepartamentoId())
                .orElseThrow(() -> new NotFoundException("Departamento nao encontrado"));

        enfermeiro.setDepartamento(departamento);
        enfermeiro.setId(enfermeiroExistente.getId());
        return enfermeiroRepository.save(enfermeiro);
    }

    public void deletarEnfermeiro(String id) {
        if (enfermeiroRepository.existsById(id)) {
            enfermeiroRepository.deleteById(id);
        } else {
            throw new NotFoundException("Enfermeiro não encontrado!");
        }
    }

    private Enfermeiro paraEnfermeiro(EnfermeiroDto enfermeiroDto) {
        var departamento = departamentoRepository.findById(enfermeiroDto.getDepartamentoId())
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

        var enfermeiro = new Enfermeiro();
        enfermeiro.setDepartamento(departamento);
        enfermeiro.setCpf(enfermeiroDto.getCpf());
        enfermeiro.setTelefone(enfermeiroDto.getTelefone());
        enfermeiro.setEndereco(verificarOuSalvarEndereco(enfermeiroDto.getEndereco()));
        enfermeiro.setDataNascimento(enfermeiroDto.getDataNascimento());
        enfermeiro.setNome(enfermeiroDto.getNome());
        return enfermeiro;
    }
    private void verificarCpfExistente(String cpf) {
        if (enfermeiroRepository.acharPorCpf(cpf).isPresent()) {
            throw new BadRequestException("Erro: CPF do Enfermeiro já cadastrado!");
        }
    }
}
