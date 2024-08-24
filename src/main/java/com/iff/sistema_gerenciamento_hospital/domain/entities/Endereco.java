package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private String id;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
}
