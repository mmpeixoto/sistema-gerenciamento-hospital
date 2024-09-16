package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.TriagemDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.EnfermeiroRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.PacienteRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.TriagemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TriagemServiceTests {

    @InjectMocks
    private TriagemService triagemService;

    @Mock
    private TriagemRepository triagemRepository;

    @Mock
    private EnfermeiroRepository enfermeiroRepository;

    @Mock
    private PacienteRepository pacienteRepository;


    @Test
    void testListarTriagens() {
        var mockTriagens = List.of(new Triagem(), new Triagem());
        when(triagemRepository.findAll()).thenReturn(mockTriagens);

        var result = triagemService.listarTriagens();

        assertEquals(2, result.size());
        verify(triagemRepository, times(1)).findAll();
    }

    @Test
    void testInserirTriagem() {
        var triagemDto = new TriagemDto();
        triagemDto.setEnfermeiroId("enf123");
        triagemDto.setPacienteId("pac123");

        var mockEnfermeiro = new Enfermeiro();
        var mockPaciente = new Paciente();
        var mockTriagem = new Triagem();

        when(enfermeiroRepository.findById(triagemDto.getEnfermeiroId())).thenReturn(Optional.of(mockEnfermeiro));
        when(pacienteRepository.findById(triagemDto.getPacienteId())).thenReturn(Optional.of(mockPaciente));
        when(triagemRepository.save(any(Triagem.class))).thenReturn(mockTriagem);

        Triagem result = triagemService.inserirTriagem(triagemDto);

        assertNotNull(result);
        verify(enfermeiroRepository, times(1)).findById(triagemDto.getEnfermeiroId());
        verify(pacienteRepository, times(1)).findById(triagemDto.getPacienteId());
        verify(triagemRepository, times(1)).save(any(Triagem.class));
    }

    @Test
    void testInserirTriagem_EnfermeiroNotFound() {
        TriagemDto triagemDto = new TriagemDto();
        triagemDto.setEnfermeiroId("enf123");
        triagemDto.setPacienteId("pac123");

        when(enfermeiroRepository.findById(triagemDto.getEnfermeiroId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            triagemService.inserirTriagem(triagemDto);
        });

        verify(enfermeiroRepository, times(1)).findById(triagemDto.getEnfermeiroId());
        verify(pacienteRepository, never()).findById(triagemDto.getPacienteId());
        verify(triagemRepository, never()).save(any(Triagem.class));
    }

    @Test
    void testInserirTriagem_PacienteNotFound() {
        TriagemDto triagemDto = new TriagemDto();
        triagemDto.setEnfermeiroId("enf123");
        triagemDto.setPacienteId("pac123");

        Enfermeiro mockEnfermeiro = new Enfermeiro();

        when(enfermeiroRepository.findById(triagemDto.getEnfermeiroId())).thenReturn(Optional.of(mockEnfermeiro));
        when(pacienteRepository.findById(triagemDto.getPacienteId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            triagemService.inserirTriagem(triagemDto);
        });

        verify(enfermeiroRepository, times(1)).findById(triagemDto.getEnfermeiroId());
        verify(pacienteRepository, times(1)).findById(triagemDto.getPacienteId());
        verify(triagemRepository, never()).save(any(Triagem.class));
    }
}
