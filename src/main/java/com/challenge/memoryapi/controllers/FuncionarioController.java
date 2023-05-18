package com.challenge.memoryapi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.memoryapi.dto.FuncionarioDto;
import com.challenge.memoryapi.models.Funcionario;
import com.challenge.memoryapi.services.FuncionarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/funcionarios")
@Api("API de Funcionários")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    @ApiOperation("Salvar funcionario.")
    @ApiResponse(code = 201, message = "Funcionario Salvo com sucesso.")
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario salvar(@Valid @RequestBody FuncionarioDto funcionarioDto) {
        log.info("FuncionarioController - Salvar funcionario.");

        return this.funcionarioService.salvar(funcionarioDto);

    }

    @PatchMapping("/{id_funcionario}")
    @ApiOperation("Atualizar funcionario")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Funcionario atualizado com sucesso."),
            @ApiResponse(code = 404, message = "Funcionario nao encontrado.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Funcionario atualizar(
            @Valid @RequestBody Funcionario funcionario,
            @PathVariable("id_funcionario") Integer idFuncionario) {
        log.info("FuncionarioController - Atualizar funcionário com id: " + idFuncionario);
        return this.funcionarioService.atualizar(funcionario, idFuncionario);

    }

    @GetMapping
    @ApiOperation("Listar todos os funcionários.")
    @ApiResponse(code = 302, message = "Funcionários encontrados com sucesso.")
    @ResponseStatus(HttpStatus.FOUND)
    public Page<Funcionario> listarTodos(
            @RequestParam(value = "pagina", defaultValue = "0", required = false) Integer pagina,
            @RequestParam(value = "numero_conteudo", defaultValue = "2", required = false) Integer numero_conteudo,
            @RequestParam(defaultValue = "id", required = false) String sortBy

    ) {
        Pageable pageble = PageRequest.of(pagina, numero_conteudo, Sort.by(sortBy));

        log.info("FuncionarioController - Listar os funcionarios.");
        return this.funcionarioService.listarTodos(pageble);
    }

    @GetMapping("/{id_funcionario}")
    @ApiOperation("Buscar funcionário.")
    @ApiResponse(code = 302, message = "Funcionários encontrados com sucesso.")
    @ResponseStatus(HttpStatus.FOUND)
    public Funcionario getFuncionario(@PathVariable("id_funcionario") Integer idFuncionario) {
        log.info("FuncionarioController - Burcar o funcionario: " + idFuncionario);

        return this.funcionarioService.getFuncionario(idFuncionario);
    }

    @DeleteMapping("/{id_funcionario}")
    @ApiOperation("Eliminar funcionario")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Funcionario eliminado com sucesso."),
            @ApiResponse(code = 404, message = "Funcionario não encontrado.")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id_funcionario") Integer idFuncionario) {
        log.info("FuncionarioController - Eliminar o funcionarios com id: " + idFuncionario);

        this.funcionarioService.eliminar(idFuncionario);
    }
}
