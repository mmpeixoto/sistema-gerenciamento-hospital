package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Column;
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
    @Column(name = "cep", nullable = false, length = 8)
    private String cep;
    @Column(name = "logradouro", nullable = false, length = 50)
    private String logradouro;
    @Column(name = "numero", nullable = false, length = 10)
    private String numero;
    @Column(name = "bairro", nullable = false, length = 20)
    private String bairro;
    @Column(name = "cidade", nullable = false, length = 20)
    private String cidade;
    @Column(name = "estado", nullable = false, length = 2)
    private String estado;
    @Column(name = "complemento", nullable = true, length = 50)
    private String complemento;
}
