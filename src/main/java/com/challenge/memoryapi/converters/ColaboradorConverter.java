package com.challenge.memoryapi.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.challenge.memoryapi.dto.ColaboradorDto;
import com.challenge.memoryapi.dto.FuncionarioDto;
import com.challenge.memoryapi.dto.ProjectoDto;
import com.challenge.memoryapi.models.Colaborador;
import com.challenge.memoryapi.models.Funcionario;
import com.challenge.memoryapi.models.Projecto;

@Component
public class ColaboradorConverter {

        @Autowired
        private ProjectoConverter projectoConverter;

        @Autowired
        private FuncionarioConverter funcionarioConverter;

        public ColaboradorDto paraColaboradorDto(Projecto projecto, List<Funcionario> listaFuncionarios) {

                List<FuncionarioDto> listaFuncionariosDto = this.funcionarioConverter
                                .paraFuncionarioDto(listaFuncionarios);

                return ColaboradorDto
                                .builder()
                                .projecto(this.projectoConverter.paraProjectoDto(projecto))
                                .funcionarios(listaFuncionariosDto)
                                .build();
        }

        public ColaboradorDto paraColaboradorDto(Colaborador colaborador) {

                FuncionarioDto listaFuncionariosDto = this.funcionarioConverter
                                .paraFuncionarioDto(colaborador.getFuncionario());

                return ColaboradorDto
                                .builder()
                                .id(colaborador.getId())
                                .projecto(this.projectoConverter.paraProjectoDto(colaborador.getProjecto()))
                                .funcionario(listaFuncionariosDto)
                                .build();
        }

        public List<ColaboradorDto> paraColaboradorDto(List<Colaborador> colaboradores) {

                return colaboradores
                                .stream()
                                .map(colaborador -> {

                                        ProjectoDto projectoDto = this.projectoConverter
                                                        .paraProjectoDto(colaborador.getProjecto());
                                        FuncionarioDto funcionarioDto = this.funcionarioConverter
                                                        .paraFuncionarioDto(colaborador.getFuncionario());

                                        ColaboradorDto colaboradorDto = ColaboradorDto
                                                        .builder()
                                                        .id(colaborador.getId())
                                                        .projecto(projectoDto)
                                                        .funcionario(funcionarioDto)
                                                        .build();

                                        return colaboradorDto;
                                }).collect(Collectors.toList());

        }
}
