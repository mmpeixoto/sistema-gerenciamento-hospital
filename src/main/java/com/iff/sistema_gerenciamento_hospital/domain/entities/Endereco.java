package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Column;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private String id;
    @ColumnDefault(value = "null")
    @Column(name = "cep", nullable = false, length = 8)
    @NotBlank(message = "CEP é obrigatorio no endereço")
    private String cep;
    @Column(name = "logradouro", nullable = false, length = 50)
    @NotBlank(message = "Logradouro é obrigatorio no endereço")
    private String logradouro;
    @Column(name = "numero", nullable = false, length = 10)
    @NotBlank(message = "Número é obrigatorio no endereço")
    private String numero;
    @Column(name = "bairro", nullable = false, length = 20)
    @NotBlank(message = "Bairro é obrigatorio no endereço")
    private String bairro;
    @Column(name = "cidade", nullable = false, length = 20)
    @NotBlank(message = "Cidade é obrigatorio no endereço")
    private String cidade;
    @Column(name = "estado", nullable = false, length = 2)
    @NotBlank(message = "Estado é obrigatorio no endereço")
    private String estado;
    @Column(name = "complemento", nullable = true, length = 50)
    private String complemento;
}
