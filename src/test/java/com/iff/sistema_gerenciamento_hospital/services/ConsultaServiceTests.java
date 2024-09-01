
package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.ConsultaRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.MedicoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ConsultaServiceTests {

    @InjectMocks
    private ConsultaService consultaService;

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private PacienteService pacienteService;

    @Mock
    private MedicoService medicoService;

    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInserirConsulta_ComSucesso() {
        Consulta consulta = new Consulta();
        Paciente paciente = new Paciente();
        paciente.setId("321");
        Medico medico = new Medico();
        medico.setId("123");
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);

        when(pacienteService.buscarPacientePorId(anyString())).thenReturn(Optional.of(paciente));
        when(medicoService.buscarMedicoPorId(anyString())).thenReturn(Optional.of(medico));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        Consulta result = consultaService.inserirConsulta(consulta);

        assertNotNull(result);
        verify(consultaRepository, times(1)).save(consulta);
    }

    @Test
    void testInserirConsulta_PacienteNaoEncontrado() {
        Consulta consulta = new Consulta();
        Paciente paciente = new Paciente();
        consulta.setPaciente(paciente);
        consulta.setMedico(new Medico());

        when(pacienteService.buscarPacientePorId(anyString())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> consultaService.inserirConsulta(consulta));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testInserirConsulta_MedicoNaoEncontrado() {
        Consulta consulta = new Consulta();
        Paciente paciente = new Paciente();
        paciente.setId("321");
        Medico medico = new Medico();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);

        when(pacienteService.buscarPacientePorId(anyString())).thenReturn(Optional.of(paciente));
        when(medicoService.buscarMedicoPorId(anyString())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> consultaService.inserirConsulta(consulta));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testListarConsultas() {
        when(consultaRepository.findAll()).thenReturn(List.of(new Consulta()));

        List<Consulta> consultas = consultaService.listarConsultas();

        assertFalse(consultas.isEmpty());
        verify(consultaRepository, times(1)).findAll();
    }

    @Test
    void testListarConsultasPaciente() {
        when(consultaRepository.acharPorIdPaciente(anyString())).thenReturn(List.of(new Consulta()));

        List<Consulta> consultas = consultaService.listarConsultasPaciente("1");

        assertFalse(consultas.isEmpty());
        verify(consultaRepository, times(1)).acharPorIdPaciente("1");
    }

    @Test
    void testListarConsultasMedico() {
        when(consultaRepository.acharPorIdMedico(anyString())).thenReturn(List.of(new Consulta()));

        List<Consulta> consultas = consultaService.listarConsultasMedico("1");

        assertFalse(consultas.isEmpty());
        verify(consultaRepository, times(1)).acharPorIdMedico("1");
    }

    @Test
    void testAtualizarConsulta_ComSucesso() {
        Consulta consultaExistente = new Consulta();
        Consulta consultaAtualizada = new Consulta();
        Paciente paciente = new Paciente();
        Medico medico = new Medico();
        paciente.setId("123");
        medico.setId("321");
        consultaAtualizada.setPaciente(paciente);
        consultaAtualizada.setMedico(medico);

        when(consultaRepository.findById(anyString())).thenReturn(Optional.of(consultaExistente));
        when(pacienteRepository.existsById(anyString())).thenReturn(true);
        when(medicoRepository.existsById(anyString())).thenReturn(true);
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consultaAtualizada);

        Consulta result = consultaService.atualizarConsulta("1", consultaAtualizada);

        assertNotNull(result);
        verify(consultaRepository, times(1)).save(consultaAtualizada);
    }

    @Test
    void testAtualizarConsulta_PacienteNaoEncontrado() {
        Consulta consultaExistente = new Consulta();
        Consulta consultaAtualizada = new Consulta();
        Paciente paciente = new Paciente();
        consultaAtualizada.setPaciente(paciente);

        when(consultaRepository.findById(anyString())).thenReturn(Optional.of(consultaExistente));
        when(pacienteRepository.existsById(anyString())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> consultaService.atualizarConsulta("1", consultaAtualizada));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testAtualizarConsulta_MedicoNaoEncontrado() {
        Consulta consultaExistente = new Consulta();
        Consulta consultaAtualizada = new Consulta();
        Medico medico = new Medico();
        consultaAtualizada.setMedico(medico);
        Paciente paciente = new Paciente();
        paciente.setId("123");
        consultaAtualizada.setPaciente(paciente);

        when(consultaRepository.findById(anyString())).thenReturn(Optional.of(consultaExistente));
        when(medicoRepository.existsById(anyString())).thenReturn(false);
        when(pacienteRepository.existsById(anyString())).thenReturn(true);

        assertThrows(NotFoundException.class, () -> consultaService.atualizarConsulta("1", consultaAtualizada));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testAtualizarConsulta_ConsultaNaoEncontrada() {
        Consulta consultaAtualizada = new Consulta();

        when(consultaRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> consultaService.atualizarConsulta("1", consultaAtualizada));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testDeletarConsulta_ComSucesso() {
        when(consultaRepository.existsById(anyString())).thenReturn(true);

        consultaService.deletarConsulta("1");

        verify(consultaRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeletarConsulta_ConsultaNaoEncontrada() {
        when(consultaRepository.existsById(anyString())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> consultaService.deletarConsulta("1"));
        verify(consultaRepository, never()).deleteById(anyString());
    }
}
