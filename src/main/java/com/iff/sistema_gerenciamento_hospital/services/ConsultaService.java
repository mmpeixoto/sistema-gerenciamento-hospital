package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.ConsultaDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.ConsultaRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.MedicoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.TriagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final TriagemRepository triagemRepository;
    private final MedicoRepository medicoRepository;

    public Consulta inserirConsulta(ConsultaDto consultaDto) {
        return consultaRepository.save(paraConsulta(consultaDto));
    }

    public Consulta inserirConsulta(Consulta consulta) {
        var medico = medicoRepository.findById(consulta.getMedico().getId())
                .orElseThrow(() -> new NotFoundException("Médico nao encontrado"));
        var triagem = triagemRepository.findById(consulta.getTriagem().getId())
                .orElseThrow(() -> new NotFoundException("Triagem nao encontrada"));
        consulta.setMedico(medico);
        consulta.setTriagem(triagem);
        return consultaRepository.save(consulta);
    }

    public List<Consulta> listarConsultas(String pacienteId, String medicoId) {
        return consultaRepository.acharPorPacienteEMedico(pacienteId, medicoId);
    }

    public Consulta getConsulta(String id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Consulta não encontrada!"));
    }

    public Consulta atualizarConsulta(String id, ConsultaDto consultaDto) {
        var consultaExistente = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Consulta não encontrada!"));

        var consultaAtualizada = paraConsulta(consultaDto);
        consultaAtualizada.setId(consultaExistente.getId());
        return consultaRepository.save(consultaAtualizada);
    }

    public Consulta atualizarConsulta(String id, Consulta consulta) {
        var consultaExistente = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Consulta não encontrada!"));
        var medico = medicoRepository.findById(consulta.getMedico().getId())
                .orElseThrow(() -> new NotFoundException("Médico nao encontrado"));
        var triagem = triagemRepository.findById(consulta.getTriagem().getId())
                .orElseThrow(() -> new NotFoundException("Triagem nao encontrada"));

        consulta.setMedico(medico);
        consulta.setTriagem(triagem);
        consulta.setId(consultaExistente.getId());
        return consultaRepository.save(consulta);
    }

    public void deletarConsulta(String id) {
        if (consultaRepository.existsById(id)){
            consultaRepository.deleteById(id);
        }
        else{
            throw new NotFoundException("Consulta não encontrada!");
        }
    }

    private Consulta paraConsulta(ConsultaDto consultaDto) {
        var medico = medicoRepository.findById(consultaDto.getMedico().getId())
                .orElseThrow(() -> new NotFoundException("Médico nao encontrado"));
        var triagem = triagemRepository.findById(consultaDto.getTriagem().getId())
                .orElseThrow(() -> new NotFoundException("Triagem nao encontrada"));

        var consulta = new Consulta();
        consulta.setMedico(medico);
        consulta.setTriagem(triagem);
        consulta.setDataConsulta(consultaDto.getDataConsulta());
        consulta.setTratamento(consultaDto.getTratamento());
        consulta.setDiagnostico(consultaDto.getDiagnostico());
        return consulta;
    }
}
