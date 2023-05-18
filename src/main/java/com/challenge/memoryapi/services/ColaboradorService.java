package com.challenge.memoryapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.memoryapi.Enums.StatusFuncionario;
import com.challenge.memoryapi.converters.ColaboradorConverter;
import com.challenge.memoryapi.dto.ColaboradorDto;
import com.challenge.memoryapi.dto.FuncionarioDto;
import com.challenge.memoryapi.exceptions.ColaboradorException;
import com.challenge.memoryapi.exceptions.FuncionarioException;
import com.challenge.memoryapi.models.Colaborador;
import com.challenge.memoryapi.models.Funcionario;
import com.challenge.memoryapi.models.Projecto;
import com.challenge.memoryapi.repositories.ColaboradorRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ColaboradorService {

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private ProjectoService projectoService;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @Autowired
    private ColaboradorConverter colaboradorConverter;

    @Transactional
    public List<ColaboradorDto> salvar(ColaboradorDto colaboradorDto) {
        log.info("ColaboradorService - Salvando colaboradores com o seu projecto.");

        Integer idProjecto = 0;
        try {
            idProjecto = colaboradorDto.getProjecto().getId();
        } catch (NullPointerException e) {
            log.error("ColaboradorService - Deve existir pelo menos um Projecto.");
            throw new ColaboradorException("Projecto inexistente. Deve existir pelo menos um Projecto.");
        }

        Projecto projecto = this.projectoService.getProjecto(idProjecto);

        List<Funcionario> listaFuncionarios = funcionarios(colaboradorDto.getFuncionarios());
        List<Colaborador> listaColaboradores = new ArrayList<>();

        listaColaboradores = listaFuncionarios
                .stream()
                .map(funcionario -> {

                    Colaborador colaborador = new Colaborador();
                    if (colaboradorDto.getId() != null) {
                        colaborador.setId(colaboradorDto.getId());
                    }
                    colaborador.setProjecto(projecto);
                    colaborador.setFuncionario(funcionario);
                    validarColaboradores(funcionario, projecto);

                    return colaborador;

                }).collect(Collectors.toList());

        listaColaboradores = this.colaboradorRepository.saveAll(listaColaboradores);
        List<ColaboradorDto> listaColaboradorsDto = this.colaboradorConverter.paraColaboradorDto(listaColaboradores);
        return listaColaboradorsDto;
    }

    public List<ColaboradorDto> atualizar(ColaboradorDto colaboradorDto, Integer idColaborador) {
        log.info("ColaboradorService - Atualizando colaborador com o seu projecto."
                + "cid_colaborador: " + idColaborador);

        Colaborador colaborador = this.findById(idColaborador);
        colaboradorDto.setId(colaborador.getId());
        List<ColaboradorDto> listaColaboradores = this.salvar(colaboradorDto);
        return listaColaboradores;

    }

    public List<Colaborador> listaColaborador() {
        log.info("ColaboradorService - Listando colaboradores com os seus projectos.");
        return this.colaboradorRepository.findAll();
    }

    public List<ColaboradorDto> getColaboradoresByProjectoIdProjecto(Integer idProjecto) {
        log.info("ColaboradorService - Listando colaboradores com o seu projecto. id_projecto: " + idProjecto);

        List<Colaborador> listaColaboradores = this.findColaboradoresByIdProjecto(idProjecto);
        List<ColaboradorDto> listaColaboradoresDto = this.colaboradorConverter.paraColaboradorDto(listaColaboradores);
        return listaColaboradoresDto;
    }

    public List<Colaborador> getColaboradoresByFuncionarioAnoAdimicao(Integer ano) {
        log.info("ColaboradorService - Listando colaboradores com o seu projecto. ano: " + ano);

        List<Colaborador> listaColaboradores = this.colaboradorRepository
                .findByFuncionarioAnoAdimicao(ano);
        return listaColaboradores;
    }

    public Colaborador findById(Integer idColaborador) {
        log.info("ColaboradorService - Buscando colaborador com id: " + idColaborador);

        return this.colaboradorRepository
                .findById(idColaborador)
                .orElseThrow(
                        () -> new ColaboradorException("Colaborador não encontrado. "
                                + "Id invalido : " + idColaborador));
    }

    public void eliminarColaboradoresByProjecto(Integer idProjecto) {
        log.info("ColaboradorService - Eliminando colaborador com id: " + idProjecto);

        List<Colaborador> listaColaboradores = this.findColaboradoresByIdProjecto(idProjecto);
        this.colaboradorRepository.deleteAll(listaColaboradores);
    }

    private List<Colaborador> findColaboradoresByIdProjecto(Integer idProjecto) {
        log.info("ColaboradorService - Buscando colaborador com id: " + idProjecto);

        Projecto projecto = this.projectoService.getProjecto(idProjecto);
        List<Colaborador> listaColaboradores = this.colaboradorRepository.findByProjecto(projecto);
        return listaColaboradores;
    }

    private List<Funcionario> funcionarios(List<FuncionarioDto> funcionariosDto) {
        log.info("ColaboradorService - Buscando os Funcionários para sem cadastrados"
                + " como colaborador ");

        try {

            return funcionariosDto
                    .stream()
                    .map(funcionario -> {
                        Funcionario fun = this.funcionarioService.getFuncionario(funcionario.getId());

                        return fun;
                    }).collect(Collectors.toList());

        } catch (NullPointerException e) {
            log.error("ColaboradorService - Deve existir pelo menos um Funcionário.");
            throw new ColaboradorException("Funcionario inexistente. Deve existir pelo menos um Funcionário.");
        }

    }

    private void validarColaboradores(Funcionario funcionario, Projecto projecto) {
        boolean isGerenteSubordinado = this.isGerente(funcionario.getFuncao().name());
        boolean isGerente = this.isGerente(projecto.getFuncionario().getFuncao().name());
        boolean isPresidenteSubordinado = this.isPresidente(funcionario.getFuncao().name());

        if (isPresidenteSubordinado) {
            log.error("ColaboradorService - Error. Um PRESIDENTE não pode ser "
                    + "subordinado. Cadastra um GERENTE ou SUBORDINADO. id_presidente: "
                    + funcionario.getId());
            throw new FuncionarioException("Error. Um PRESIDENTE não pode "
                    + "ser subordinado. Cadastra um GERENTE ou SUBORDINADO");

        } else if (isGerente && isGerenteSubordinado) {
            log.error("ColaboradorService - Error. Um GERENTE não pode ser subordinado "
                    + "de um outro GERENTE. id_funcionario: " + funcionario.getId());
            throw new FuncionarioException(
                    "Error. Um GERENTE não pode ser subordinado de um outro GERENTE. "
                            + "Cadastra um SUBORDINADO");

        }

    }

    private boolean isGerente(String funcao) {
        return (funcao == StatusFuncionario.GERENTE.name());
    }

    private boolean isPresidente(String funcao) {
        return (funcao == StatusFuncionario.PRESIDENTE.name());
    }
}