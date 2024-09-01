package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.PacienteRepository;

import static org.assertj.core.api.Assertions.assertThatNoException;
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
    void inserirPaciente_quandoCpfForNulo_deveRetornarErro() {
        // Arrange
        var paciente = mock(Paciente.class);

        // Act & Assert
        thenThrownBy(() -> pacienteService.inserirPaciente(paciente))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Erro: O CPF é necessário para o cadastro do paciente");
    }

    @Test
    void inserirPaciente_quandoCpfJaExiste_deveRetornarErro() {
        // Arrange
        var paciente = new Paciente();
        paciente.setCpf("123456789");

        given(pacienteRepository.acharPorCpf(paciente.getCpf())).willReturn(Optional.of(paciente));
        // Act & Assert
        thenThrownBy(() -> pacienteService.inserirPaciente(paciente))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Erro: CPF do cliente já cadastrado!");
    }

    @Test
    void inserirPaciente_quandoCadastradoSemEndereco_deveRetornarErro() {
        // Arrange
        var paciente = new Paciente();
        paciente.setEndereco(null);
        paciente.setCpf("123412342");

        given(pacienteRepository.acharPorCpf(paciente.getCpf())).willReturn(Optional.empty());

        // Act & Assert
        thenThrownBy(() -> pacienteService.inserirPaciente(paciente))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Erro: O endereço é necessário para o cadastro do cliente");
    }

    @Test
    void inserirPaciente_quandoCadastradoCorretamente_deveSalvarPaciente() {
        // Arrange
        var paciente = new Paciente();
        paciente.setEndereco(new Endereco());
        paciente.setCpf("123412342");

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

        thenThrownBy(() -> pacienteService.buscarPacientePorCpf(""))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Paciente não encontrado!");
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

    @Test
    void buscarPacientePorId_quandoExistePaciente_deveRetornar() {
        var paciente = new Paciente();
        paciente.setId("123");

        given(pacienteRepository.findById(paciente.getId())).willReturn(Optional.of(paciente));

        var pacienteEncontrado = pacienteService.buscarPacientePorId("123");

        assertThat(pacienteEncontrado).isEqualTo(Optional.of(paciente));
    }

    @Test
    void buscarPacientePorId_quandoNaoExistePaciente_deveLancarExcessao() {
        given(pacienteRepository.findById("123")).willReturn(Optional.empty());

        thenThrownBy(() -> pacienteService.buscarPacientePorId("123"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Paciente não encontrado!");
    }

    @Test
    void deletarPaciente_quandoPacienteExistente_deveDeletarPaciente() {
        var paciente = new Paciente();
        paciente.setId("123");
        given(pacienteRepository.existsById(paciente.getId())).willReturn(true);

        assertThatNoException().isThrownBy(() -> pacienteService.deletarPaciente("123"));
    }

    @Test
    void deletarPaciente_quandoPacienteNaoExistente_deveRetornarErro() {
        given(pacienteRepository.existsById("123")).willReturn(false);

        thenThrownBy(() -> pacienteService.deletarPaciente("123"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Paciente não encontrado!");
    }

    @Test
    void atualizarPaciente_quandoPacienteNaoExistente_DeveRetornarErro(){
        given(pacienteRepository.findById("123")).willReturn(Optional.empty());

        thenThrownBy(() -> pacienteService.atualizarPaciente("123", new Paciente()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Paciente não encontrado!");
    }

    @Test
    void atualizarPaciente_quandoCpfJaExiste_DeveRetornarErro() {
        var pacienteExistente = new Paciente();
        var novoPaciente = new Paciente();
        novoPaciente.setCpf("12341234");
        novoPaciente.setId("123");
        pacienteExistente.setCpf("12345");
        given(pacienteRepository.findById("123")).willReturn(Optional.of(pacienteExistente));
        given(pacienteRepository.acharPorCpf("12341234")).willReturn(Optional.of(novoPaciente));

        thenThrownBy(() -> pacienteService.atualizarPaciente("123", novoPaciente))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Erro: CPF do cliente já cadastrado!");
    }

    @Test
    void atualizarPaciente_quandoEnderecoNulo_DeveRetornarErro() {
        var pacienteExistente = new Paciente();
        var novoPaciente = new Paciente();
        novoPaciente.setCpf("12341234");
        novoPaciente.setId("123");
        pacienteExistente.setCpf("12345");
        given(pacienteRepository.findById("123")).willReturn(Optional.of(pacienteExistente));
        given(pacienteRepository.acharPorCpf("12341234")).willReturn(Optional.empty());

        thenThrownBy(() -> pacienteService.atualizarPaciente("123", novoPaciente))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Erro: O endereço é necessário para o cadastro do cliente");
    }

    @Test
    void atualizarPaciente_quandoEnderecoPresente_deveAtualizar() {
        var pacienteExistente = new Paciente();
        var novoPaciente = new Paciente();
        novoPaciente.setCpf("12341234");
        pacienteExistente.setCpf("12341234");
        novoPaciente.setId("123");
        novoPaciente.setEndereco(new Endereco());
        given(pacienteRepository.findById("123")).willReturn(Optional.of(pacienteExistente));

        assertThatNoException().isThrownBy(() -> pacienteService.atualizarPaciente("123", novoPaciente));
    }
}
