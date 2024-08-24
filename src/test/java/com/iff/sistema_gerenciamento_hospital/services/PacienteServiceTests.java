package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.PacienteRepository;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTests {
    @Mock
    private PacienteRepository pacienteRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @InjectMocks
    private PacienteService pacienteService;

    @Test
    void inserirPaciente_quandoCpfJaExiste_deveRetornarErro(){
        // Arrange
        var paciente = mock(Paciente.class);

        given(pacienteRepository.acharPorCpf(paciente.getCpf())).willReturn(Optional.of(paciente));

        // Act & Assert
        thenThrownBy(() -> pacienteService.inserirPaciente(paciente))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Erro: CPF do cliente ja cadastrado!");
    }

    @Test
    void inserirPaciente_quandoCadastradoSemEndereco_deveRetornarErro(){
        // Arrange
        var paciente = mock(Paciente.class);
        paciente.setEndereco(null);

        given(pacienteRepository.acharPorCpf(paciente.getCpf())).willReturn(Optional.empty());

        // Act & Assert
        thenThrownBy(() -> pacienteService.inserirPaciente(paciente))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Erro: O endereço é necessário para o cadastro do cliente");
    }
}
