package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Medico inserirMedico(Medico medico) {
        verificarLicencaExistente(medico.getLicenca());

        verificarCpfExistente(medico.getCpf());

        if (medico.getCpf() == null) {
            throw new BadRequestException("Erro: O CPF é necessário para o cadastro do médico");
        }

        if (medico.getLicenca() == null) {
            throw new BadRequestException("Erro: O número de licença é necessário para o cadastro do médico");
        }

        Endereco endereco = verificarOuSalvarEndereco(medico.getEndereco());

        medico.setEndereco(endereco);
        return medicoRepository.save(medico);
    }

    public List<Medico> listarMedicos() {
        return medicoRepository.findAll();
    }

    public Optional<Medico> buscarMedicoPorId(String id) {
        if (medicoRepository.findById(id).isEmpty()) {
            throw new BadRequestException("Erro: Não existe médico com esse ID!");
        }
        return medicoRepository.findById(id);
    }

    public Optional<Medico> buscarMedicoPorLicenca(String licenca) {
        if (medicoRepository.acharPorLicenca(licenca).isEmpty()) {
            throw new BadRequestException("Erro: Nenhum médico foi cadastrado com esse número de licença");
        }
        return medicoRepository.acharPorLicenca(licenca);
    }

    public Medico atualizarMedico(String id, Medico medico){
        Medico medicoExistente = medicoRepository.findById(id).orElseThrow(() -> new NotFoundException("Médico não encontrado!"));

        if (!medicoExistente.getCpf().equals(medico.getCpf())) {
            verificarCpfExistente(medico.getCpf());
        }

        if (!medicoExistente.getLicenca().equals(medico.getLicenca())) {
            verificarLicencaExistente(medico.getLicenca());
        }

        Endereco endereco = verificarOuSalvarEndereco(medico.getEndereco());

        medico.setId(id);
        medico.setEndereco(endereco);

        return medicoRepository.save(medico);
    }

    public void deletarMedico(String id) {
        if (medicoRepository.existsById(id)){
            medicoRepository.deleteById(id);
        }
        else{
            throw new NotFoundException("Médico não encontrado!");
        }
    }

    private void verificarCpfExistente(String cpf) {
        if (medicoRepository.acharPorCpf(cpf).isPresent()) {
            throw new BadRequestException("Erro: CPF do médico já cadastrado!");
        }
    }

    private void verificarLicencaExistente(String licenca) {
        if (medicoRepository.acharPorLicenca(licenca).isPresent()) {
            throw new BadRequestException("Erro: Médico com está licença já cadastrado!");
        }
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
}
