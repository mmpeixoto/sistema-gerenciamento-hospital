package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.PacienteRepository;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTests {
    @Mock
    private PacienteRepository pacienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    void inserirPaciente_quandoCpfJaExiste_deveRetornarErro() {
        // Arrange
        var paciente = mock(Paciente.class);

        given(pacienteRepository.acharPorCpf(paciente.getCpf())).willReturn(Optional.of(paciente));
        // Act & Assert
        thenThrownBy(() -> pacienteService.inserirPaciente(paciente))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Erro: CPF do cliente ja cadastrado!");
    }

    @Test
    void inserirPaciente_quandoCadastradoSemEndereco_deveRetornarErro() {
        // Arrange
        var paciente = mock(Paciente.class);
        paciente.setEndereco(null);

        given(pacienteRepository.acharPorCpf(paciente.getCpf())).willReturn(Optional.empty());

        // Act & Assert
        thenThrownBy(() -> pacienteService.inserirPaciente(paciente))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Erro: O endereço é necessário para o cadastro do cliente");
    }

    @Test
    void inserirPaciente_quandoCadastradoCorretamente_deveSalvarPaciente() {
        // Arrange
        var paciente = mock(Paciente.class);
        var endereco = mock(Endereco.class);

        given(paciente.getEndereco()).willReturn(endereco);
        given(pacienteRepository.acharPorCpf(paciente.getCpf())).willReturn(Optional.empty());
        given(pacienteRepository.save(paciente)).willReturn(paciente);

        // Act
        Paciente resultado = pacienteService.inserirPaciente(paciente);

        // Assert
        assertThat(resultado).isEqualTo(paciente);
    }

    @Test
    void buscarPacientePorCpf_quandoCpfExistente_deveRetornarPaciente() {
        var paciente = mock(Paciente.class);

        given(pacienteRepository.acharPorCpf(paciente.getCpf())).willReturn(Optional.of(paciente));
        var pacienteEncontrado = pacienteService.buscarPacientePorCpf(paciente.getCpf());

        assertThat(pacienteEncontrado).isEqualTo(Optional.of(paciente));
    }

    @Test
    void buscarPacientePorCpf_quandoCpfNaoExistente_deveRetornarNulo() {
        given(pacienteRepository.acharPorCpf("")).willReturn(Optional.empty());

        var pacienteEncontrado = pacienteService.buscarPacientePorCpf("");

        assertThat(pacienteEncontrado).isEqualTo(Optional.empty());

    }

    @Test
    void listarPaciente_quandoPacientesExistentes_deveRetornarLista() {
        var paciente1 = mock(Paciente.class);
        var paciente2 = mock(Paciente.class);

        given(pacienteRepository.findAll()).willReturn(List.of(paciente1, paciente2));

        var pacientesEncontrados = pacienteService.listarPacientes();
        assertThat(pacientesEncontrados).containsExactly(paciente1, paciente2);
    }

    @Test
    void listarPaciente_quandoNaoExistemPacientes_deveRetornarListaVazia() {
        given(pacienteRepository.findAll()).willReturn(List.of());

        var pacientesEncontrados = pacienteService.listarPacientes();

        assertThat(pacientesEncontrados).isEmpty();
    }
}
