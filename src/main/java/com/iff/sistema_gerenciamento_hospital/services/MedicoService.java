package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.MedicoDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.DepartamentoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;
    private final EnderecoRepository enderecoRepository;
    private final DepartamentoRepository departamentoRepository;

    public Medico inserirMedico(MedicoDto medicoDto) {
        if (medicoDto.getCpf() == null) {
            throw new BadRequestException("Erro: O CPF é necessário para o cadastro do médico");
        }

        if (medicoDto.getLicenca() == null) {
            throw new BadRequestException("Erro: O número de licença é necessário para o cadastro do médico");
        }
        verificarLicencaExistente(medicoDto.getLicenca());
        verificarCpfExistente(medicoDto.getCpf());

        return medicoRepository.save(paraMedico(medicoDto));
    }

    public Medico inserirMedico(Medico medico) {
        var departamento = departamentoRepository.findById(medico.getDepartamento().getId())
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));

        verificarLicencaExistente(medico.getLicenca());
        verificarCpfExistente(medico.getCpf());
        verificarOuSalvarEndereco(medico.getEndereco());
        medico.setDepartamento(departamento);
        return medicoRepository.save(medico);
    }

    public List<Medico> listarMedicos() {
        return medicoRepository.findAll();
    }

    public Medico buscarMedicoPorId(String id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Erro: Não existe médico com esse ID!"));
    }

    public Medico buscarMedicoPorLicenca(String licenca) {
        return medicoRepository.acharPorLicenca(licenca).orElseThrow(() -> new NotFoundException("Erro: Não existe médico com essa licença!"));
    }

    public Medico atualizarMedico(String id, Medico medico){
        Medico medicoExistente = medicoRepository.findById(id).orElseThrow(() -> new NotFoundException("Médico não encontrado!"));
        var departamento = departamentoRepository.findById(medico.getDepartamento().getId())
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));
        if (!medicoExistente.getCpf().equals(medico.getCpf())) {
            verificarCpfExistente(medico.getCpf());
        }

        if (!medicoExistente.getLicenca().equals(medico.getLicenca())) {
            verificarLicencaExistente(medico.getLicenca());
        }

        Endereco endereco = verificarOuSalvarEndereco(medico.getEndereco());

        medico.setId(id);
        medico.setEndereco(endereco);
        medico.setDepartamento(departamento);

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

    private Medico paraMedico(MedicoDto medicoDto) {
        var departamento = departamentoRepository.findById(medicoDto.getDepartamento().getId())
                .orElseThrow(() -> new NotFoundException("Departamento não encontrado"));
        var medico = new Medico();
        medico.setDepartamento(departamento);
        medico.setLicenca(medicoDto.getLicenca());
        medico.setEndereco(verificarOuSalvarEndereco(medicoDto.getEndereco()));
        medico.setTelefone(medicoDto.getTelefone());
        medico.setNome(medicoDto.getNome());
        medico.setDataNascimento(medicoDto.getDataNascimento());
        medico.setCpf(medicoDto.getCpf());
        medico.setEspecialidade(medicoDto.getEspecialidade());
        return medico;
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
