package com.challenge.memoryapi.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuncionarioDto {
    private Integer id;

    @NotBlank(message = "Campo nome não pode estar vazio.")
    private String nome;

    @CPF(message = "CPF invalido.")
    private String cpf;

    private String dataAdmicao;

    @Min(0)
    private BigDecimal remuneracao;

    @NotBlank(message = "Campo função não pode estar vazio.")
    private String funcao;
}
