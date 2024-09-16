package com.iff.sistema_gerenciamento_hospital.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

@Data
public class EnderecoDto {
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
