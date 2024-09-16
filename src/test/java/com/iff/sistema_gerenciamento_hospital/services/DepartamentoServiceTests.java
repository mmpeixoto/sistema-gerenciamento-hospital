package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.ChefeDepartamentoDto;
import com.iff.sistema_gerenciamento_hospital.domain.dtos.DepartamentoDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.DepartamentoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.MedicoRepository;
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
public class DepartamentoServiceTests {

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Mock
    private MedicoRepository medicoRepository;

    @InjectMocks
    private DepartamentoService departamentoService;

    @Test
    void testListarDepartamentos() {
       var mockDepartamentos = List.of(new Departamento(), new Departamento());
        when(departamentoRepository.findAll()).thenReturn(mockDepartamentos);

        var result = departamentoService.listarDepartamentos();

        assertEquals(2, result.size());
        verify(departamentoRepository, times(1)).findAll();
    }

    @Test
    void testInserirDepartamento() {
        var dto = new DepartamentoDto();
        var mockDepartamento = new Departamento();

        when(departamentoRepository.save(any(Departamento.class))).thenReturn(mockDepartamento);

        var result = departamentoService.inserirDepartamento(dto);

        assertNotNull(result);
        verify(departamentoRepository, times(1)).save(any(Departamento.class));
    }

    @Test
    void testEditarChefeDepartamento_Success() {
        var departamentoId = "dep123";
        var chefeDepartamentoDto = new ChefeDepartamentoDto();
        chefeDepartamentoDto.setChefeDepartamentoId("med123");

        var mockDepartamento = new Departamento();
        var mockChefe = new Medico();

        when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.of(mockDepartamento));
        when(medicoRepository.findById("med123")).thenReturn(Optional.of(mockChefe));
        when(departamentoRepository.save(mockDepartamento)).thenReturn(mockDepartamento);

        var result = departamentoService.editarChefeDepartamento(departamentoId, chefeDepartamentoDto);

        assertNotNull(result);
        assertEquals(mockChefe, mockDepartamento.getChefeDeDepartamento());
        verify(departamentoRepository, times(1)).findById(departamentoId);
        verify(medicoRepository, times(1)).findById("med123");
        verify(departamentoRepository, times(1)).save(mockDepartamento);
    }

    @Test
    void testEditarChefeDepartamento_DepartamentoNotFound() {
        var departamentoId = "dep123";
        var chefeDepartamentoDto = new ChefeDepartamentoDto();
        chefeDepartamentoDto.setChefeDepartamentoId("med123");

        when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            departamentoService.editarChefeDepartamento(departamentoId, chefeDepartamentoDto);
        });

        verify(departamentoRepository, times(1)).findById(departamentoId);
        verify(medicoRepository, never()).findById(anyString());
        verify(departamentoRepository, never()).save(any(Departamento.class));
    }

    @Test
    void testEditarChefeDepartamento_MedicoNotFound() {
        var departamentoId = "dep123";
        var chefeDepartamentoDto = new ChefeDepartamentoDto();
        chefeDepartamentoDto.setChefeDepartamentoId("med123");

        var mockDepartamento = new Departamento();

        when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.of(mockDepartamento));
        when(medicoRepository.findById("med123")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            departamentoService.editarChefeDepartamento(departamentoId, chefeDepartamentoDto);
        });

        verify(departamentoRepository, times(1)).findById(departamentoId);
        verify(medicoRepository, times(1)).findById("med123");
        verify(departamentoRepository, never()).save(any(Departamento.class));
    }
}
