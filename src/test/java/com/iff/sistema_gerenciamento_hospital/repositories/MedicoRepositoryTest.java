package com.iff.sistema_gerenciamento_hospital.repositories;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
public class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void acharPoLicenca_quandoLicencaExistente_deveRetornarMedico(){
        var medico = new Medico();
        medico.setLicenca("Licenca123");
        medico.setNome("Dr. Médico");

        entityManager.persist(medico);

        Optional<Medico> medicoEncontrado = medicoRepository.acharPorLicenca(medico.getLicenca());

        assertThat(medicoEncontrado).isPresent().contains(medico);
    }

    @Test
    void acharPorCpf_quandoCpfExistente_deveRetornarMedico(){
        var medico = new Medico();
        medico.setCpf("10293847561");
        medico.setLicenca("Licenca123");

        entityManager.persist(medico);

        Optional<Medico> medicoEncontrado = medicoRepository.acharPorCpf(medico.getCpf());

        assertThat(medicoEncontrado).isPresent().contains(medico);
    }
    
    @Test
    void salvar_quandoNovoMedico_deveSalvarComSucesso(){
        var medico = new Medico();
        medico.setLicenca("Licenca123");
        medico.setNome("Medico 1");
        medico.setCpf("12312312312");

        Medico medicoSalvo = medicoRepository.save(medico);

        assertThat(entityManager.find(Medico.class, medicoSalvo.getId())).isEqualTo(medicoSalvo);
    }

    @Test
    void acharPorId_quandoIdExistente_deveRetornarMedico(){
        var medico = new Medico();

        entityManager.persist(medico);

        Optional<Medico> medicoEncontrado = medicoRepository.findById(medico.getId());

        assertThat(medicoEncontrado).isPresent().contains(medico);
    }

    @Test
    void listarMedico_quandoMedicosSalvos_retornaSucesso(){
        var medico1 = new Medico();
        medico1.setLicenca("Licenca123");

        var medico2 = new Medico();
        medico2.setLicenca("Licenca456");

        medicoRepository.save(medico1);
        medicoRepository.save(medico2);

        Iterable<Medico> medicos = medicoRepository.findAll();

        assertThat(medicos).hasSize(2);
    }

    @Test
    void buscarPorLicenca_quandoLicencaNaoExiste_deveRetornarVazio(){
        Optional<Medico> medicoEncontrado = medicoRepository.acharPorLicenca("licenca1");

        assertThat(medicoEncontrado).isNotPresent();
    }

    @Test
    void buscarPorCpf_quandoCpfNaoExistente_deveRetornarVazio(){
        Optional<Medico> medicoEncotrado = medicoRepository.acharPorCpf("000.111.111-23");

        assertThat(medicoEncotrado).isNotPresent();
    }

    @Test
    void deletarMedico_quandoIdExistente_deveDeletarMedico(){
        var medico = new Medico();
        medico.setLicenca("Licenca123");
        medico.setNome("Medico 1");
        medico.setCpf("12312312312");

        entityManager.persist(medico);
        medicoRepository.deleteById(medico.getId());

        Optional<Medico> medicoDeletado = medicoRepository.findById(medico.getId());

        assertThat(medicoDeletado).isNotPresent();
    }

    @Test
    void atualizarMedico_quandoMedicoExistenteModificado_deveAtualizarMedico(){
        var medico = new Medico();
        medico.setLicenca("Licenca123");
        medico.setNome("Medico 1");
        medico.setCpf("12312312312");

        entityManager.persist(medico);

        Medico medicoExistente = medicoRepository.findById(medico.getId()).orElseThrow();
        medicoExistente.setNome("Médico 2");

        Medico medicoAtualizado = medicoRepository.save(medicoExistente);
        
        assertThat(medicoAtualizado.getNome()).isEqualTo("Médico 2");
        assertThat(medicoAtualizado.getLicenca()).isEqualTo("Licenca123");
        assertThat(medicoAtualizado.getCpf()).isEqualTo("12312312312");
        assertThat(entityManager.find(Medico.class, medicoAtualizado.getId())).isEqualTo(medicoAtualizado);
    }

    @Test
    void deletarMedico_quandoEsseNaoPersistidoNoBanco_naoDeveDeletarMedico(){
        var medicoNaoPersistido = new Medico();
        medicoNaoPersistido.setId("id_");
        medicoNaoPersistido.setLicenca("Licenca123");
        medicoNaoPersistido.setNome("medico1");
        medicoNaoPersistido.setCpf("12312312312");

        medicoRepository.delete(medicoNaoPersistido);

        Optional<Medico> medicoEncontrado = medicoRepository.findById(medicoNaoPersistido.getId());
        assertThat(medicoEncontrado).isNotPresent();
    }

}
