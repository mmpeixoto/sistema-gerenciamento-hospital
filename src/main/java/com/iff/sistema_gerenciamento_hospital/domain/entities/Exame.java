package com.iff.sistema_gerenciamento_hospital.domain.entities;

import com.iff.sistema_gerenciamento_hospital.domain.enums.TiposDeExame;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Exame {
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private String id;
    @Column(name = "data", nullable = false)
    private Date data;
    @Column(name = "resultado", nullable = true, length = 50)
    private String resultado;
    private TiposDeExame tiposDeExame;
    @ManyToOne
    private Consulta consulta;
}

