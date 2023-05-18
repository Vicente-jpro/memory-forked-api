package com.challenge.memoryapi.converters;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.challenge.memoryapi.Enums.StatusFuncionario;
import com.challenge.memoryapi.dto.FuncionarioDto;
import com.challenge.memoryapi.models.Funcionario;

@Component
public class FuncionarioConverter {

    public FuncionarioDto paraFuncionarioDto(Funcionario funcionario) {
        return FuncionarioDto
                .builder()
                .id(funcionario.getId())
                .nome(funcionario.getNome())
                .cpf(funcionario.getCpf())
                .dataAdmicao(String.valueOf(funcionario.getDataAdmicao()))
                .remuneracao(funcionario.getRemuneracao())
                .funcao(String.valueOf(funcionario.getFuncao()))
                .build();
    }

    public Funcionario paraFuncionario(FuncionarioDto funcionarioDto) {
        LocalDate data = null;
        if (funcionarioDto.getDataAdmicao() != null) {
            data = LocalDate.parse(funcionarioDto.getDataAdmicao(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return Funcionario
                .builder()
                .id(funcionarioDto.getId())
                .nome(funcionarioDto.getNome())
                .cpf(funcionarioDto.getCpf())
                .dataAdmicao(data)
                .remuneracao(funcionarioDto.getRemuneracao())
                .funcao(StatusFuncionario.valueOf(funcionarioDto.getFuncao()))
                .build();
    }

    public List<FuncionarioDto> paraFuncionarioDto(List<Funcionario> funcionarios) {

        return funcionarios
                .stream()
                .map(funcionario -> {
                    FuncionarioDto funcionarioDto = FuncionarioDto
                            .builder()
                            .id(funcionario.getId())
                            .nome(funcionario.getNome())
                            .cpf(funcionario.getCpf())
                            .dataAdmicao(String.valueOf(funcionario.getDataAdmicao()))
                            .remuneracao(funcionario.getRemuneracao())
                            .funcao(String.valueOf(funcionario.getFuncao()))
                            .build();

                    return funcionarioDto;
                }).collect(Collectors.toList());

    }
}
