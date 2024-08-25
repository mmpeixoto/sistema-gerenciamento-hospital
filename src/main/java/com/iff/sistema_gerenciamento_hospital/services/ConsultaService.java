package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.repositories.ConsultaRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.MedicoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    public Consulta inserirConsulta(Consulta consulta) {
        Optional<Paciente> paciente = pacienteService.buscarPacientePorId(consulta.getPaciente().getId());
        Optional<Medico> medico = medicoService.buscarMedicoPorId(consulta.getMedico().getId());

        if (paciente.isEmpty()) {
            throw new BadRequestException("Erro: Paciente não encontrado");
        }

        if (medico.isEmpty()) {
            throw new BadRequestException("Erro: Médico não encontrado");
        }

        return consultaRepository.save(consulta);
    }

    public List<Consulta> listarConsultas() {
        return consultaRepository.findAll();
    }

    public List<Consulta> listarConsultasPaciente(String pacienteId) {
        return consultaRepository.acharPorIdPaciente(pacienteId);
    }

    public List<Consulta> listarConsultasMedico(String medicoId) {
        return consultaRepository.acharPorIdMedico(medicoId);
    }
}
