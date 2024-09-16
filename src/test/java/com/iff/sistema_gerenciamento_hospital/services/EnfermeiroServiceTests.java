package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.EnfermeiroDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.DepartamentoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.EnfermeiroRepository;
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
public class EnfermeiroServiceTests {

    @InjectMocks
    private EnfermeiroService enfermeiroService;

    @Mock
    private EnfermeiroRepository enfermeiroRepository;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Test
    void testListarEnfermeiros() {
        List<Enfermeiro> mockEnfermeiros = List.of(new Enfermeiro(), new Enfermeiro());
        when(enfermeiroRepository.findAll()).thenReturn(mockEnfermeiros);

        List<Enfermeiro> result = enfermeiroService.listarEnfermeiros();

        assertEquals(2, result.size());
        verify(enfermeiroRepository, times(1)).findAll();
    }

    @Test
    void testInserirEnfermeiro() {
        var enfermeiroDto = new EnfermeiroDto();
        enfermeiroDto.setDepartamentoId("dep123");
        var endereco = new Endereco();
        endereco.setCep("cep");
        endereco.setLogradouro("logradouro");
        endereco.setComplemento("complemento");
        endereco.setNumero("numero");
        enfermeiroDto.setEndereco(endereco);

        Departamento mockDepartamento = new Departamento();
        Enfermeiro mockEnfermeiro = new Enfermeiro();
        var mockEndereco = new Endereco();

        when(departamentoRepository.findById(enfermeiroDto.getDepartamentoId())).thenReturn(Optional.of(mockDepartamento));
        when(enderecoRepository.acharPorCepLogradouroNumeroComplemento(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.of(mockEndereco));
        when(enfermeiroRepository.save(any(Enfermeiro.class))).thenReturn(mockEnfermeiro);

        Enfermeiro result = enfermeiroService.inserirEnfermeiro(enfermeiroDto);

        assertNotNull(result);
        verify(departamentoRepository, times(1)).findById(enfermeiroDto.getDepartamentoId());
        verify(enderecoRepository, times(1)).acharPorCepLogradouroNumeroComplemento(anyString(), anyString(), anyString(), anyString());
        verify(enfermeiroRepository, times(1)).save(any(Enfermeiro.class));
    }

    @Test
    void testInserirEnfermeiro_DepartamentoNotFound() {
        EnfermeiroDto enfermeiroDto = new EnfermeiroDto();
        enfermeiroDto.setDepartamentoId("dep123");

        when(departamentoRepository.findById(enfermeiroDto.getDepartamentoId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            enfermeiroService.inserirEnfermeiro(enfermeiroDto);
        });

        verify(departamentoRepository, times(1)).findById(enfermeiroDto.getDepartamentoId());
        verify(enderecoRepository, never()).acharPorCepLogradouroNumeroComplemento(anyString(), anyString(), anyString(), anyString());
        verify(enfermeiroRepository, never()).save(any(Enfermeiro.class));
    }

    @Test
    void testInserirEnfermeiro_EnderecoNull() {
        EnfermeiroDto enfermeiroDto = new EnfermeiroDto();
        enfermeiroDto.setEndereco(null);
        enfermeiroDto.setDepartamentoId("dep123");

        when(departamentoRepository.findById(enfermeiroDto.getDepartamentoId())).thenReturn(Optional.of(new Departamento()));
        assertThrows(BadRequestException.class, () -> {
            enfermeiroService.inserirEnfermeiro(enfermeiroDto);
        });

        verify(enderecoRepository, never()).acharPorCepLogradouroNumeroComplemento(anyString(), anyString(), anyString(), anyString());
        verify(enfermeiroRepository, never()).save(any(Enfermeiro.class));
    }
}
