package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Departamento {
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private String id;
    private String localizacao;
    @ManyToOne
    private Medico chefeDeDepartamento;
    @OneToMany(mappedBy = "departamento")
    private List<Medico> medicos;
    @OneToMany(mappedBy = "departamento")
    private List<Enfermeiro> enfermeiros;
}
