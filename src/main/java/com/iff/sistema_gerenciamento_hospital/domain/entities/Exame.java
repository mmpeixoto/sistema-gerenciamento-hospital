package com.iff.sistema_gerenciamento_hospital.domain.entities;

import com.iff.sistema_gerenciamento_hospital.domain.enums.TiposDeExame;
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
    private Date data;
    private String resultado;
    private TiposDeExame tiposDeExame;
    @ManyToOne
    private Consulta consulta;
}
