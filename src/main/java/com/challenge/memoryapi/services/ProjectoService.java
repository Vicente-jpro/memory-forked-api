package com.challenge.memoryapi.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.memoryapi.Enums.StatusFuncionario;
import com.challenge.memoryapi.converters.FuncionarioConverter;
import com.challenge.memoryapi.converters.ProjectoConverter;
import com.challenge.memoryapi.dto.ProjectoDto;
import com.challenge.memoryapi.exceptions.FuncionarioException;
import com.challenge.memoryapi.exceptions.ProjectoNotFoundException;
import com.challenge.memoryapi.models.Funcionario;
import com.challenge.memoryapi.models.Projecto;
import com.challenge.memoryapi.repositories.ProjectoRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ProjectoService {

    @Autowired
    private ProjectoRepository projectoRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private FuncionarioConverter funcionarioConverter;

    @Autowired
    private ProjectoConverter projectoConverter;

    private static String ERRO_AO_SALVAR_O_PROJECTO = "Erro ao salvar o projecto."
            + " Não é permitido cadastrar um SUBORDINADO para gerenciar o prejecto.";

    public ProjectoDto salvar(ProjectoDto projectoDto) {
        log.info("ProjectoService - Salvando o projecto.");

        Integer idFuncionario = projectoDto.getFuncionario().getId();
        Funcionario funcionario = this.funcionarioService.getFuncionario(idFuncionario);

        if (isSubordinado(funcionario.getFuncao().name())) {
            log.error(ERRO_AO_SALVAR_O_PROJECTO);

            throw new FuncionarioException(ERRO_AO_SALVAR_O_PROJECTO);
        }
        Projecto projecto = new Projecto();
        projecto.setId(projectoDto.getId());
        projecto.setDescricao(projectoDto.getDescricao());
        projecto.setFuncionario(funcionario);

        LocalDate data = LocalDate.parse(projectoDto.getDataEntrega(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        projecto.setDataEntrega(data);

        this.projectoRepository.save(projecto);
        projectoDto.setId(projecto.getId());
        projectoDto.setFuncionario(funcionarioConverter.paraFuncionarioDto(funcionario));

        return projectoDto;

    }

    public ProjectoDto atualisar(ProjectoDto projectoDto, Integer idProjecto) {
        log.info("ProjectoService - Eliminando o projecto com id: " + idProjecto);

        Projecto projecto = this.getProjecto(idProjecto);
        if (projecto != null) {
            projectoDto.setId(idProjecto);
            return this.salvar(projectoDto);
        }
        return null;
    }

    public List<Projecto> listarProjetos() {
        log.info("ProjectoService - Listando os projectos");
        return this.projectoRepository.findAll();
    }

    public ProjectoDto buscarProjecto(Integer idProjecto) {
        log.info("ProjectoService - Buscando projecto com id: " + idProjecto);
        Projecto projecto = this.getProjecto(idProjecto);
        ProjectoDto projectoDto = this.projectoConverter.paraProjectoDto(projecto);

        return projectoDto;
    }

    public void eliminar(Integer idProjectos) {
        log.info("ProjectoService - Eliminado o projecto com id: " + idProjectos);

        Projecto projecto = this.getProjecto(idProjectos);
        this.projectoRepository.deleteById(projecto.getId());
    }

    public Projecto getProjecto(Integer idProjecto) {
        return this.projectoRepository
                .findById(idProjecto)
                .orElseThrow(
                        () -> new ProjectoNotFoundException("Projecto não encontrado. Id: "
                                + idProjecto));
    }

    public boolean isSubordinado(String funcao) {
        return (funcao == StatusFuncionario.SUBORDINADO.name());

    }

}
