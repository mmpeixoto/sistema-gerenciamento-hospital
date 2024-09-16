
package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.dtos.MedicoDto;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Departamento;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.DepartamentoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.MedicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
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
class MedicoServiceTests {

    @InjectMocks
    private MedicoService medicoService;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Test
    void testInserirMedico_ComSucesso() {
        var medico = new MedicoDto();
        medico.setCpf("12345678900");
        medico.setLicenca("MED12345");
        medico.setDepartamentoId("123");
        var endereco = new Endereco();
        endereco.setCep("cep");
        endereco.setLogradouro("logradouro");
        endereco.setComplemento("complemento");
        endereco.setNumero("numero");
        medico.setEndereco(endereco);

        when(departamentoRepository.findById("123")).thenReturn(Optional.of(new Departamento()));
        when(medicoRepository.save(any(Medico.class))).then(AdditionalAnswers.returnsFirstArg());
        when(enderecoRepository.acharPorCepLogradouroNumeroComplemento(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.of(endereco));

        var result = medicoService.inserirMedico(medico);

        assertNotNull(result);
        assertEquals("12345678900", result.getCpf());
        assertEquals("MED12345", result.getLicenca());
        verify(medicoRepository, times(1)).save(any(Medico.class));
    }

    @Test
    void testInserirMedico_ComCpfDuplicado() {
        var medico = new MedicoDto();
        medico.setCpf("12345678900");
        medico.setLicenca("123");

        when(medicoRepository.acharPorCpf(anyString())).thenReturn(Optional.of(new Medico()));

        assertThrows(BadRequestException.class, () -> medicoService.inserirMedico(medico));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testInserirMedico_ComLicencaDuplicada() {
        var medico = new MedicoDto();
        medico.setCpf("12345678900");
        medico.setLicenca("MED12345");

        when(medicoRepository.acharPorLicenca(anyString())).thenReturn(Optional.of(new Medico()));

        assertThrows(BadRequestException.class, () -> medicoService.inserirMedico(medico));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testInserirMedico_SemEndereco() {
        var medico = new MedicoDto();
        medico.setCpf("12345678900");
        medico.setLicenca("MED12345");
        medico.setDepartamentoId("123");
        medico.setEndereco(null);

        when(departamentoRepository.findById("123")).thenReturn(Optional.of(new Departamento()));

        assertThrows(BadRequestException.class, () -> medicoService.inserirMedico(medico));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testInserirMedico_SemCpf() {
        var medico = new MedicoDto();
        medico.setLicenca("MED12345");
        medico.setEndereco(null);

        assertThrows(BadRequestException.class, () -> medicoService.inserirMedico(medico));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testInserirMedico_SemLicenca() {
        var medico = new MedicoDto();
        medico.setCpf("123");
        medico.setEndereco(null);

        assertThrows(BadRequestException.class, () -> medicoService.inserirMedico(medico));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testListarMedicos() {
        when(medicoRepository.findAll()).thenReturn(List.of(new Medico()));

        List<Medico> medicos = medicoService.listarMedicos();

        assertFalse(medicos.isEmpty());
        verify(medicoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarMedicoPorId_Encontrado() {
        var medico = new Medico();
        when(medicoRepository.findById(anyString())).thenReturn(Optional.of(medico));

        var result = medicoService.buscarMedicoPorId("1");

        assertNotNull(result);
        verify(medicoRepository, times(1)).findById("1");
    }

    @Test
    void testBuscarMedicoPorId_NaoEncontrado() {
        when(medicoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> medicoService.buscarMedicoPorId("1"));
        verify(medicoRepository, times(1)).findById("1");
    }

    @Test
    void testBuscarMedicoPorLicenca_Encontrado() {
        var medico = new Medico();
        when(medicoRepository.acharPorLicenca(anyString())).thenReturn(Optional.of(medico));

        var result = medicoService.buscarMedicoPorLicenca("MED12345");

        assertNotNull(result);
        verify(medicoRepository, times(1)).acharPorLicenca("MED12345");
    }

    @Test
    void testBuscarMedicoPorLicenca_NaoEncontrado() {
        when(medicoRepository.acharPorLicenca(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> medicoService.buscarMedicoPorLicenca("MED12345"));
        verify(medicoRepository, times(1)).acharPorLicenca("MED12345");
    }

    @Test
    void testAtualizarMedico_ComSucesso() {
        var medicoExistente = new Medico();
        medicoExistente.setCpf("12345678900");
        medicoExistente.setLicenca("MED12345");
        var medicoAtualizado = new Medico();
        medicoAtualizado.setCpf("12345678900");
        medicoAtualizado.setLicenca("MED12345");
        var endereco = new Endereco();
        endereco.setCep("cep");
        endereco.setLogradouro("logradouro");
        endereco.setComplemento("complemento");
        endereco.setNumero("numero");
        medicoAtualizado.setEndereco(endereco);

        when(medicoRepository.findById(anyString())).thenReturn(Optional.of(medicoExistente));
        when(medicoRepository.save(any(Medico.class))).thenReturn(medicoAtualizado);
        when(enderecoRepository.acharPorCepLogradouroNumeroComplemento(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.of(new Endereco()));

        var result = medicoService.atualizarMedico("1", medicoAtualizado);

        assertNotNull(result);
        verify(medicoRepository, times(1)).save(medicoAtualizado);
    }

    @Test
    void testAtualizarMedico_ComCpfDuplicado() {
        var medicoExistente = new Medico();
        medicoExistente.setCpf("12345678900");
        var medicoAtualizado = new Medico();
        medicoAtualizado.setCpf("99999999999");

        when(medicoRepository.findById(anyString())).thenReturn(Optional.of(medicoExistente));
        when(medicoRepository.acharPorCpf("99999999999")).thenReturn(Optional.of(new Medico()));

        assertThrows(BadRequestException.class, () -> medicoService.atualizarMedico("1", medicoAtualizado));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testAtualizarMedico_ComLicencaDuplicada() {
        var medicoExistente = new Medico();
        medicoExistente.setLicenca("MED12345");
        medicoExistente.setCpf("123");
        var medicoAtualizado = new Medico();
        medicoAtualizado.setLicenca("MED67890");
        medicoAtualizado.setCpf("456");

        when(medicoRepository.findById(anyString())).thenReturn(Optional.of(medicoExistente));
        when(medicoRepository.acharPorLicenca("MED67890")).thenReturn(Optional.of(new Medico()));

        assertThrows(BadRequestException.class, () -> medicoService.atualizarMedico("1", medicoAtualizado));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testAtualizarMedico_ComLicencaDiferenteMasNaoDuplicada() {
        var medicoExistente = new Medico();
        medicoExistente.setLicenca("MED12345");
        medicoExistente.setCpf("123");
        var medicoAtualizado = new Medico();
        medicoAtualizado.setLicenca("MED67890");
        medicoAtualizado.setCpf("qwe");

        when(medicoRepository.findById(anyString())).thenReturn(Optional.of(medicoExistente));
        when(medicoRepository.acharPorLicenca("MED67890")).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> medicoService.atualizarMedico("1", medicoAtualizado));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testAtualizarMedico_IdNaoEncontrado() {
        when(medicoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> medicoService.atualizarMedico("1", new Medico()));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testDeletarMedico_ComSucesso() {
        when(medicoRepository.existsById(anyString())).thenReturn(true);

        medicoService.deletarMedico("1");

        verify(medicoRepository, times(1)).deleteById("1");
    }

    @Test
    void testDeletarMedico_NaoEncontrado() {
        when(medicoRepository.existsById(anyString())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> medicoService.deletarMedico("1"));
        verify(medicoRepository, never()).deleteById(anyString());
    }
}
