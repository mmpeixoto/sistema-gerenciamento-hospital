package com.iff.sistema_gerenciamento_hospital.repositories;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
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
class EnderecoRepositoryTest {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void acharPorCepLogradouroNumeroComplemento_quandoEnderecoExistente_deveRetornarEndereco() {
        Endereco endereco = new Endereco();
        endereco.setCep("12345678");
        endereco.setLogradouro("Rua 1");
        endereco.setNumero("100");
        endereco.setComplemento("Apt 101");

        entityManager.persist(endereco);

        Optional<Endereco> enderecoEncontrado = enderecoRepository.acharPorCepLogradouroNumeroComplemento(
                endereco.getCep(), endereco.getLogradouro(), endereco.getNumero(), endereco.getComplemento());

        assertThat(enderecoEncontrado).isPresent().contains(endereco);
    }

    @Test
    void salvar_quandoNovoEndereco_deveSalvarComSucesso() {
        Endereco endereco = new Endereco();
        endereco.setId("Id123");

        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        assertThat(entityManager.find(Endereco.class, enderecoSalvo.getId())).isEqualTo(enderecoSalvo);
    }

    @Test
    void acharPorId_quandoIdExistente_deveRetornarEndereco() {
        Endereco endereco = new Endereco();
        entityManager.persist(endereco);

        Optional<Endereco> enderecoEncontrado = enderecoRepository.findById(endereco.getId());

        assertThat(enderecoEncontrado).isPresent().contains(endereco);
    }

    @Test
    void listarEnderecos_quandoEnderecosSalvos_retornaSucesso() {
        Endereco endereco1 = new Endereco();
        entityManager.persist(endereco1);

        Endereco endereco2 = new Endereco();
        entityManager.persist(endereco2);

        Iterable<Endereco> enderecos = enderecoRepository.findAll();

        assertThat(enderecos).hasSize(2);
    }

    @Test
    void buscarPorCepLogradouroNumeroComplemento_quandoEnderecoNaoExiste_deveRetornarVazio() {
        Optional<Endereco> enderecoEncontrado = enderecoRepository.acharPorCepLogradouroNumeroComplemento(
                "00000-000", "Rua Inexistente", "999", "Sem complemento");

        assertThat(enderecoEncontrado).isNotPresent();
    }

    @Test
    void deletarEndereco_quandoIdExistente_deveDeletarEndereco() {
        Endereco endereco = new Endereco();

        entityManager.persist(endereco);

        enderecoRepository.deleteById(endereco.getId());

        Optional<Endereco> enderecoDeletado = enderecoRepository.findById(endereco.getId());

        assertThat(enderecoDeletado).isNotPresent();
    }

    @Test
    void atualizarEndereco_quandoEnderecoExistenteModificado_deveAtualizarEndereco() {
        Endereco endereco = new Endereco();
        endereco.setCep("55555-555");
        endereco.setLogradouro("Rua dos Jacarandás");
        endereco.setNumero("400");
        endereco.setComplemento("Casa");

        entityManager.persist(endereco);

        Endereco enderecoExistente = enderecoRepository.findById(endereco.getId()).orElseThrow();
        enderecoExistente.setNumero("401");

        Endereco enderecoAtualizado = enderecoRepository.save(enderecoExistente);

        assertThat(enderecoAtualizado.getNumero()).isEqualTo("401");
        assertThat(enderecoAtualizado.getLogradouro()).isEqualTo("Rua dos Jacarandás");
        assertThat(enderecoAtualizado.getCep()).isEqualTo("55555-555");
        assertThat(enderecoAtualizado.getComplemento()).isEqualTo("Casa");
        assertThat(entityManager.find(Endereco.class, enderecoAtualizado.getId())).isEqualTo(enderecoAtualizado);
    }

    @Test
    void deletarEndereco_quandoEsseNaoPersistidoNoBanco_naoDeveDeletarEndereco() {
        Endereco enderecoNaoPersistido = new Endereco();
        enderecoNaoPersistido.setId("id_inexistente");
        enderecoNaoPersistido.setCep("11111-111");
        enderecoNaoPersistido.setLogradouro("Rua Sem Nome");
        enderecoNaoPersistido.setNumero("999");
        enderecoNaoPersistido.setComplemento("Sem complemento");

        enderecoRepository.delete(enderecoNaoPersistido);

        Optional<Endereco> enderecoEncontrado = enderecoRepository.findById(enderecoNaoPersistido.getId());
        assertThat(enderecoEncontrado).isNotPresent();
    }
}
