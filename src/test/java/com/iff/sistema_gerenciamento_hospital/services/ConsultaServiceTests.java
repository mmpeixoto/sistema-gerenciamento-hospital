
package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.ConsultaDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.ConsultaRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.MedicoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.TriagemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultaServiceTests {

    @InjectMocks
    private ConsultaService consultaService;

    @Mock
    private TriagemRepository triagemRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private ConsultaRepository consultaRepository;

    @Test
    void testInserirConsulta_ComSucesso() {
        var consultaDto = new ConsultaDto();
        consultaDto.setMedicoId("medicoId");
        consultaDto.setTriagemId("triagemId");
        var consulta = new Consulta();
        when(triagemRepository.findById("triagemId")).thenReturn(Optional.of(new Triagem()));
        when(medicoRepository.findById("medicoId")).thenReturn(Optional.of(new Medico()));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consulta);

        Consulta result = consultaService.inserirConsulta(consultaDto);

        assertNotNull(result);
        verify(consultaRepository, times(1)).save(consulta);
    }

    @Test
    void testInserirConsulta_MedicoNaoEncontrado() {
        var consultaDto = new ConsultaDto();
        consultaDto.setMedicoId("medicoId");

        when(medicoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> consultaService.inserirConsulta(consultaDto));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testInserirConsulta_TriagemNaoEncontrada() {
        var consultaDto = new ConsultaDto();
        consultaDto.setMedicoId("medicoId");
        consultaDto.setTriagemId("triagemId");

        when(medicoRepository.findById("medicoId")).thenReturn(Optional.of(new Medico()));
        when(triagemRepository.findById("triagemId")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> consultaService.inserirConsulta(consultaDto));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testListarConsultas() {
        when(consultaRepository.acharPorPacienteEMedico("", "")).thenReturn(List.of(new Consulta()));

        List<Consulta> consultas = consultaService.listarConsultas("", "");

        assertFalse(consultas.isEmpty());
        verify(consultaRepository, times(1)).acharPorPacienteEMedico("", "");
    }

    @Test
    void testAtualizarConsulta_ComSucesso() {
        var consultaExistente = new Consulta();
        var consultaAtualizada = new Consulta();
        var consultaBody = new ConsultaDto();
        var triagem = new Triagem();
        var medico = new Medico();
        consultaBody.setMedicoId("123");
        consultaBody.setTriagemId("123");

        when(consultaRepository.findById(anyString())).thenReturn(Optional.of(consultaExistente));
        when(triagemRepository.findById(anyString())).thenReturn(Optional.of(triagem));
        when(medicoRepository.findById(anyString())).thenReturn(Optional.of(medico));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consultaAtualizada);

        Consulta result = consultaService.atualizarConsulta("1", consultaBody);

        assertNotNull(result);
        verify(consultaRepository, times(1)).save(consultaAtualizada);
    }

    @Test
    void testAtualizarConsulta_TriagemNaoEncontrada() {
        var consultaExistente = new Consulta();
        var consultaBody = new ConsultaDto();
        consultaBody.setTriagemId("123");
        consultaBody.setMedicoId("123");
        var medico = new Medico();

        when(consultaRepository.findById(anyString())).thenReturn(Optional.of(consultaExistente));
        when(triagemRepository.findById(anyString())).thenReturn(Optional.empty());
        when(medicoRepository.findById(anyString())).thenReturn(Optional.of(medico));

        assertThrows(NotFoundException.class, () -> consultaService.atualizarConsulta("1", consultaBody));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testAtualizarConsulta_MedicoNaoEncontrado() {
        var consultaExistente = new Consulta();
        var consultaAtualizada = new ConsultaDto();
        consultaAtualizada.setMedicoId("123");

        when(consultaRepository.findById(anyString())).thenReturn(Optional.of(consultaExistente));
        when(medicoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> consultaService.atualizarConsulta("1", consultaAtualizada));
        verify(consultaRepository, never()).save(any(Consulta.class));
    }

    @Test
    void testAtualizarConsulta_ConsultaNaoEncontrada() {
        var consultaDto = new ConsultaDto();

        when(consultaRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> consultaService.atualizarConsulta("1", consultaDto));
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
