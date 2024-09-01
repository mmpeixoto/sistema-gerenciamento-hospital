package com.iff.sistema_gerenciamento_hospital.repositories;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Endereco;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface EnderecoRepository extends JpaRepository<Endereco, String> {
    @Query("select e from Endereco e where e.cep=?1 and e.logradouro=?2 and e.numero=?3 and e.complemento=?4")
    Optional<Endereco> acharPorCepLogradouroNumeroComplemento(String cep, String logradouro, String numero, String complemento);
}
