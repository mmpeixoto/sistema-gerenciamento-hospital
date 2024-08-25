package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Medico inserirMedico(Medico medico) {
        if (medicoRepository.acharPorLicenca(medico.getLicenca()).isPresent()){
            throw new BadRequestException("Erro: Médico com está licença já cadastrado!");
        }

        if (medico.getEndereco() == null) {
            throw new BadRequestException("Erro: O endereço é necessário para o cadastro do médico");
        }

        enderecoRepository.save(medico.getEndereco());
        return medicoRepository.save(medico);
    }
    public List<Medico> listarMedicos() {return medicoRepository.findAll(); }
}
