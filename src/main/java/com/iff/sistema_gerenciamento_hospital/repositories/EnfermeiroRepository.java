package com.iff.sistema_gerenciamento_hospital.repositories;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Enfermeiro;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

=======
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
>>>>>>> origin/main
public interface EnfermeiroRepository extends JpaRepository<Enfermeiro, String> {
}
