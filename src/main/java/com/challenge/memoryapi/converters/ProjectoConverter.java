package com.challenge.memoryapi.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.memoryapi.dto.ProjectoDto;
import com.challenge.memoryapi.models.Projecto;

@Component
public class ProjectoConverter {

    @Autowired
    private FuncionarioConverter funcionarioConverter;

    public List<ProjectoDto> paraProjectoDto(List<Projecto> projectos) {

        return projectos
                .stream()
                .map(projecto -> {
                    ProjectoDto pro = this.paraProjectoDto(projecto);

                    return pro;
                }).collect(Collectors.toList());

    }

    public ProjectoDto paraProjectoDto(Projecto projecto) {

        return ProjectoDto
                .builder()
                .id(projecto.getId())
                .descricao(projecto.getDescricao())
                .dataEntrega(String.valueOf(projecto.getDataEntrega()))
                .funcionario(funcionarioConverter.paraFuncionarioDto(projecto.getFuncionario()))
                .build();
    }

}
