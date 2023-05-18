package com.challenge.memoryapi.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectoDto {

    private Integer id;

    @NotBlank(message = "Campo descrição não pode estar vazio.")
    private String descricao;

    @NotBlank(message = "Campo dataEntrega não pode estar vazio.")
    private String dataEntrega;
    private FuncionarioDto funcionario;

}
