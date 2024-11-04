package com.iff.sistema_gerenciamento_hospital.domain.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;


@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Departamento {
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private String id;
    @NotBlank(message = "Localização é obrigatoria para o departamento")
    @Column(name = "localizacao", nullable = false, length = 100)
    private String localizacao;
    @ManyToOne
    @JsonIgnoreProperties("departamento")
    private Medico chefeDeDepartamento;

    @Transient
    private String chefeDeDepartamentoId;
}
