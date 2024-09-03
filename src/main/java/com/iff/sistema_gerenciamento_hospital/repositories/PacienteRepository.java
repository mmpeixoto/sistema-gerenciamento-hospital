package com.iff.sistema_gerenciamento_hospital.repositories;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Paciente;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Transactional
public interface PacienteRepository extends JpaRepository<Paciente, String> {

    @Query("select p from Paciente p where p.cpf=?1")
    Optional<Paciente> acharPorCpf(String cpf);
}
