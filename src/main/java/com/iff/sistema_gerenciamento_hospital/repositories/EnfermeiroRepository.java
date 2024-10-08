package com.iff.sistema_gerenciamento_hospital.repositories;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface EnfermeiroRepository extends JpaRepository<Enfermeiro, String> {

    @Query("select e from Enfermeiro e where e.cpf=?1")
    Optional<Enfermeiro> acharPorCpf(String cpf);
}