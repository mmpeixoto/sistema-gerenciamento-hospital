package com.iff.sistema_gerenciamento_hospital.repositories;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Consulta;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ConsultaRepository extends JpaRepository<Consulta, String> {

    @Query("select c from Consulta c where c.paciente.id=?1")
    List<Consulta> acharPorIdPaciente(String id);


    @Query("select c from Consulta c where c.medico.id=?1")
    List<Consulta> acharPorIdMedico(String id);
}
