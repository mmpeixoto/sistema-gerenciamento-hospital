package com.iff.sistema_gerenciamento_hospital.repositories;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Triagem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface TriagemRepository extends JpaRepository<Triagem, String> {
}
