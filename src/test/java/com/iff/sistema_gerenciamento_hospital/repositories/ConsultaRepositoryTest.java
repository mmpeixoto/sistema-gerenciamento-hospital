package com.iff.sistema_gerenciamento_hospital.repositories;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
public class ConsultaRepositoryTest {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void acharPorIdPaciente_quandoPacienteExistente_deveRetornarConsultas() {
        var paciente = new Paciente();
        paciente.setNome("Nome 1");
        paciente.setCpf("12312312300");

        entityManager.persist(paciente);

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);

        entityManager.persist(consulta);

        List<Consulta> consultasEncontradas = consultaRepository.acharPorIdPaciente(paciente.getId());

        assertThat(consultasEncontradas).isNotEmpty();
        assertThat(consultasEncontradas).contains(consulta);
    }

    @Test
    void acharPorIdMedico_quandoMedicoExistente_deveRetornarConsultas() {
        Medico medico = new Medico();
        medico.setNome("Medico 1");
        medico.setCpf("12312312311");
        medico.setLicenca("Licenca 1");

        entityManager.persist(medico);

        Consulta consulta = new Consulta();
        consulta.setMedico(medico);

        entityManager.persist(consulta);

        List<Consulta> consultasEncontradas = consultaRepository.acharPorIdMedico(medico.getId());

        assertThat(consultasEncontradas).isNotEmpty();
        assertThat(consultasEncontradas).contains(consulta);
    }

    @Test
    void salvar_quandoNovaConsulta_deveSalvarComSucesso(){
        Paciente paciente = new Paciente();
        paciente.setNome("Nome 1");
        paciente.setCpf("12312312300");

        Medico medico = new Medico();
        medico.setNome("Medico 1");
        medico.setCpf("12312312311");
        medico.setLicenca("Licenca 1");

        entityManager.persist(paciente);
        entityManager.persist(medico);

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);

        Consulta consultaSalva = consultaRepository.save(consulta);

        assertThat(entityManager.find(Consulta.class, consultaSalva.getId())).isEqualTo(consultaSalva);
    }

    @Test
    void acharPorID_quandoIDExistente_deveRetornarConsulta(){
        Consulta consulta = new Consulta();
        entityManager.persist(consulta);

        Optional<Consulta> consultaEncontrada = consultaRepository.findById(consulta.getId());

        assertThat(consultaEncontrada).isPresent().contains(consulta);
    }

    @Test
    void listarConsultas_quandoConsultasSalvas_retornaSucesso(){
        Consulta consulta1 = new Consulta();
        entityManager.persist(consulta1);

        Consulta consulta2 = new Consulta();
        entityManager.persist(consulta2);

        Iterable<Consulta> consultas = consultaRepository.findAll();

        assertThat(consultas).hasSize(2);
    }

    @Test
    void buscarPorIdPaciente_quandoIdPacienteNaoExiste_retornaVazio(){
        List<Consulta> consultasEncontradas = consultaRepository.acharPorIdPaciente("IdNumero1");
        assertThat(consultasEncontradas).isEmpty();
    }

    @Test
    void buscarPorIdMedico_quandoIdMedicoNaoExiste_retornaVazio(){
        List<Consulta> consultasEncontradas = consultaRepository.acharPorIdMedico("IdNumero1");
        assertThat(consultasEncontradas).isEmpty();
    }

    @Test
    void deletarConsulta_quandoIdConsultaExistente_deveDeletar(){
        var consulta = new Consulta();
        entityManager.persist(consulta);

        consultaRepository.deleteById(consulta.getId());

        Optional<Consulta> consultaDeletada = consultaRepository.findById(consulta.getId());
        assertThat(consultaDeletada).isNotPresent();
    }

    @Test
    void atualizarConsulta_quandoConsultaExistenteModificada_deveAtualizar(){
        Consulta consulta = new Consulta();
        consulta.setTratamento("Soro");
        consulta.setDiagnostico("Resfriado");
        entityManager.persist(consulta);

        Consulta consultaExistente = consultaRepository.findById(consulta.getId()).orElseThrow();
        consultaExistente.setTratamento("Repouso");

        Consulta consultaAtualizada = consultaRepository.save(consultaExistente);

        assertThat(consultaAtualizada.getTratamento()).isEqualTo("Repouso");
        assertThat(consultaAtualizada.getDiagnostico()).isEqualTo("Resfriado");
        assertThat(entityManager.find(Consulta.class, consultaAtualizada.getId())).isEqualTo(consultaAtualizada);
    }

    @Test
    void deletarConsulta_quandoEssaNaoPersistidaNoBanco_naoDeveDeletar(){
        var consultaNaoPersistida = new Consulta();
        consultaNaoPersistida.setId("Id1");

        consultaRepository.delete(consultaNaoPersistida);

        Optional<Consulta> consultaEncontrada = consultaRepository.findById(consultaNaoPersistida.getId());
        assertThat(consultaEncontrada).isNotPresent();
    }
}







