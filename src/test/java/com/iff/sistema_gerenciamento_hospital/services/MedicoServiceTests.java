
package com.iff.sistema_gerenciamento_hospital.services;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.BadRequestException;
import com.iff.sistema_gerenciamento_hospital.domain.exceptions.NotFoundException;
import com.iff.sistema_gerenciamento_hospital.repositories.EnderecoRepository;
import com.iff.sistema_gerenciamento_hospital.repositories.MedicoRepository;
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

class MedicoServiceTests {

    @InjectMocks
    private MedicoService medicoService;

    @Mock
    private MedicoRepository medicoRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInserirMedico_ComSucesso() {
        Medico medico = new Medico();
        medico.setCpf("12345678900");
        medico.setLicenca("MED12345");
        Endereco endereco = new Endereco();
        medico.setEndereco(endereco);

        when(medicoRepository.save(any(Medico.class))).thenReturn(medico);
        when(enderecoRepository.acharPorCepLogradouroNumeroComplemento(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.of(endereco));

        Medico result = medicoService.inserirMedico(medico);

        assertNotNull(result);
        assertEquals("12345678900", result.getCpf());
        assertEquals("MED12345", result.getLicenca());
        verify(medicoRepository, times(1)).save(medico);
    }

    @Test
    void testInserirMedico_ComCpfDuplicado() {
        Medico medico = new Medico();
        medico.setCpf("12345678900");

        when(medicoRepository.acharPorCpf(anyString())).thenReturn(Optional.of(new Medico()));

        assertThrows(BadRequestException.class, () -> medicoService.inserirMedico(medico));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testInserirMedico_ComLicencaDuplicada() {
        Medico medico = new Medico();
        medico.setCpf("12345678900");
        medico.setLicenca("MED12345");

        when(medicoRepository.acharPorCpf(anyString())).thenReturn(Optional.empty());
        when(medicoRepository.acharPorLicenca(anyString())).thenReturn(Optional.of(new Medico()));

        assertThrows(BadRequestException.class, () -> medicoService.inserirMedico(medico));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testInserirMedico_SemEndereco() {
        Medico medico = new Medico();
        medico.setCpf("12345678900");
        medico.setLicenca("MED12345");
        medico.setEndereco(null);

        assertThrows(BadRequestException.class, () -> medicoService.inserirMedico(medico));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testInserirMedico_SemCpf() {
        Medico medico = new Medico();
        medico.setLicenca("MED12345");
        medico.setEndereco(null);

        assertThrows(BadRequestException.class, () -> medicoService.inserirMedico(medico));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testInserirMedico_SemLicenca() {
        Medico medico = new Medico();
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
        Medico medico = new Medico();
        when(medicoRepository.findById(anyString())).thenReturn(Optional.of(medico));

        Optional<Medico> result = medicoService.buscarMedicoPorId("1");

        assertTrue(result.isPresent());
        verify(medicoRepository, times(2)).findById("1");
    }

    @Test
    void testBuscarMedicoPorId_NaoEncontrado() {
        when(medicoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> medicoService.buscarMedicoPorId("1"));
        verify(medicoRepository, times(1)).findById("1");
    }

    @Test
    void testBuscarMedicoPorLicenca_Encontrado() {
        Medico medico = new Medico();
        when(medicoRepository.acharPorLicenca(anyString())).thenReturn(Optional.of(medico));

        Optional<Medico> result = medicoService.buscarMedicoPorLicenca("MED12345");

        assertTrue(result.isPresent());
        verify(medicoRepository, times(2)).acharPorLicenca("MED12345");
    }

    @Test
    void testBuscarMedicoPorLicenca_NaoEncontrado() {
        when(medicoRepository.acharPorLicenca(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> medicoService.buscarMedicoPorLicenca("MED12345"));
        verify(medicoRepository, times(1)).acharPorLicenca("MED12345");
    }

    @Test
    void testAtualizarMedico_ComSucesso() {
        Medico medicoExistente = new Medico();
        medicoExistente.setCpf("12345678900");
        medicoExistente.setLicenca("MED12345");
        Medico medicoAtualizado = new Medico();
        medicoAtualizado.setCpf("12345678900");
        medicoAtualizado.setLicenca("MED12345");
        medicoAtualizado.setEndereco(new Endereco());

        when(medicoRepository.findById(anyString())).thenReturn(Optional.of(medicoExistente));
        when(medicoRepository.save(any(Medico.class))).thenReturn(medicoAtualizado);
        when(enderecoRepository.acharPorCepLogradouroNumeroComplemento(anyString(), anyString(), anyString(), anyString())).thenReturn(Optional.of(new Endereco()));

        Medico result = medicoService.atualizarMedico("1", medicoAtualizado);

        assertNotNull(result);
        verify(medicoRepository, times(1)).save(medicoAtualizado);
    }

    @Test
    void testAtualizarMedico_ComCpfDuplicado() {
        Medico medicoExistente = new Medico();
        medicoExistente.setCpf("12345678900");
        Medico medicoAtualizado = new Medico();
        medicoAtualizado.setCpf("99999999999");

        when(medicoRepository.findById(anyString())).thenReturn(Optional.of(medicoExistente));
        when(medicoRepository.acharPorCpf("99999999999")).thenReturn(Optional.of(new Medico()));

        assertThrows(BadRequestException.class, () -> medicoService.atualizarMedico("1", medicoAtualizado));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testAtualizarMedico_ComLicencaDuplicada() {
        Medico medicoExistente = new Medico();
        medicoExistente.setLicenca("MED12345");
        medicoExistente.setCpf("123");
        Medico medicoAtualizado = new Medico();
        medicoAtualizado.setLicenca("MED67890");
        medicoAtualizado.setCpf("456");

        when(medicoRepository.findById(anyString())).thenReturn(Optional.of(medicoExistente));
        when(medicoRepository.acharPorLicenca("MED67890")).thenReturn(Optional.of(new Medico()));

        assertThrows(BadRequestException.class, () -> medicoService.atualizarMedico("1", medicoAtualizado));
        verify(medicoRepository, never()).save(any(Medico.class));
    }

    @Test
    void testAtualizarMedico_ComLicencaDiferenteMasNaoDuplicada() {
        Medico medicoExistente = new Medico();
        medicoExistente.setLicenca("MED12345");
        medicoExistente.setCpf("123");
        Medico medicoAtualizado = new Medico();
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
