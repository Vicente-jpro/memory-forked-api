package com.challenge.memoryapi.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColaboradorDto {
    private Integer id;
    @NotEmpty
    private ProjectoDto projecto;
    private FuncionarioDto funcionario;
    @NotEmpty
    private List<FuncionarioDto> funcionarios;
}
