package com.iff.sistema_gerenciamento_hospital.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.UuidGenerator;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {
    @Id
    @UuidGenerator
    @EqualsAndHashCode.Include
    private String id;
    @NotBlank(message = "CEP é obrigatorio no endereço")
    private String cep;
    @NotBlank(message = "Logradouro é obrigatorio no endereço")
    private String logradouro;
    @NotBlank(message = "Número é obrigatorio no endereço")
    private String numero;
    @NotBlank(message = "Bairro é obrigatorio no endereço")
    private String bairro;
    @NotBlank(message = "Cidade é obrigatorio no endereço")
    private String cidade;
    @NotBlank(message = "Estado é obrigatorio no endereço")
    private String estado;
    @ColumnDefault(value = "null")
    private String complemento;
}
