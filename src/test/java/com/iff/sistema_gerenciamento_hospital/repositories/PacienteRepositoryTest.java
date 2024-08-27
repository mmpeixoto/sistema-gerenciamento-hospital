package com.iff.sistema_gerenciamento_hospital.repositories;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
class PacienteRepositoryTest {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void acharPorCpf_quandoCpfExistenteFornecido_deveRetornarPaciente() {
        var paciente = new Paciente();
        paciente.setCpf("123456789");
        paciente.setNome("Jorge");

        entityManager.persist(paciente);

        Optional<Paciente> pacienteEncontrado = pacienteRepository.acharPorCpf(paciente.getCpf());

        assertThat(pacienteEncontrado).isPresent().contains(paciente);

    }

    @Test
    void salvar_quandoNovoPaciente_deveSalvarComSucesso() {
        Paciente paciente = new Paciente();
        paciente.setCpf("123456789");
        paciente.setNome("Joao");

        Paciente pacienteSalvo = pacienteRepository.save(paciente);

        assertThat(entityManager.find(Paciente.class, pacienteSalvo.getId())).isEqualTo(pacienteSalvo);
    }

    @Test
    void acharPorId_quandoIdExistenteFornecido_deveRetornarPaciente() {
        var paciente = new Paciente();

        entityManager.persist(paciente);

        Optional<Paciente> pacienteEncontrado = pacienteRepository.findById(paciente.getId());

        assertThat(pacienteEncontrado).isPresent().contains(paciente);
    }

    @Test
    void listarPacientes_quandoVariosPacientsSalvos_retornaSucesso() {
        Paciente paciente1 = new Paciente();
        paciente1.setCpf("123456789");

        Paciente paciente2 = new Paciente();
        paciente2.setCpf("987654321");

        pacienteRepository.save(paciente1);
        pacienteRepository.save(paciente2);

        Iterable<Paciente> pacientes = pacienteRepository.findAll();

        assertThat(pacientes).hasSize(2);
    }

    @Test
    void buscarPorCpf_quandoCpfNaoExiste_deveRetornar_vazio() {
        Optional<Paciente> pacienteEncontrado = pacienteRepository.acharPorCpf("000.000.000-00");

        assertThat(pacienteEncontrado).isNotPresent();
    }

}