package com.iff.sistema_gerenciamento_hospital.repositories;

import com.iff.sistema_gerenciamento_hospital.domain.entities.Medico;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

<<<<<<< HEAD

=======
>>>>>>> origin/main
@Repository
@Transactional
public interface MedicoRepository extends JpaRepository<Medico, String> {

    @Query("select m from Medico m where m.licenca=?1")
    Optional<Medico> acharPorLicenca(String licenca);

    @Query("select m from Medico m where m.cpf=?1")
    Optional<Medico> acharPorCpf(String cpf);
<<<<<<< HEAD
}
=======
}
>>>>>>> origin/main
