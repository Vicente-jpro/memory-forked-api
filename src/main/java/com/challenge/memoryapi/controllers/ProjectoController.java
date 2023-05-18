package com.challenge.memoryapi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.memoryapi.converters.ProjectoConverter;
import com.challenge.memoryapi.dto.ProjectoDto;
import com.challenge.memoryapi.models.Projecto;
import com.challenge.memoryapi.services.ProjectoService;
import com.challenge.memoryapi.utils.PaginacaoProject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/projectos")
@Api("API de projectos.")
public class ProjectoController {

    @Autowired
    private ProjectoService projectoService;

    @Autowired
    private ProjectoConverter projectoConverter;

    @Autowired
    private PaginacaoProject paginacaoProject;

    @PostMapping
    @ApiOperation("Savar projecto com seu gerente ou presidente.")
    @ApiResponse(code = 201, message = "Projecto salvo com sucesso.")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectoDto salvar(@Valid @RequestBody ProjectoDto projectoDto) {
        log.info("ProjectoController - Salvar projecto com o seu Gerente.");
        return this.projectoService.salvar(projectoDto);
    }

    @PatchMapping("/{id_projecto}")
    @ApiOperation("Atualizar projecto com seu gerente ou presidente.")
    @ApiResponse(code = 201, message = "Projecto atualizado com sucesso.")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectoDto atualizar(
            @Valid @RequestBody ProjectoDto projectoDto,
            @PathVariable("id_projecto") Integer idProjecto) {
        log.info("ProjectoController - Atualizar projecto com o seu Gerente.");
        return this.projectoService.atualisar(projectoDto, idProjecto);
    }

    @GetMapping
    @ApiOperation("Listar projectos")
    @ApiResponses({
            @ApiResponse(code = 302, message = "Projectos encontrados com sucesso."),
            @ApiResponse(code = 404, message = "Não foram encontrados nenhum projecto.")
    })
    @ResponseStatus(HttpStatus.FOUND)
    public Page<ProjectoDto> listarProjectos(
            @PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("ProjectoController - Listar os projectos.");

        List<Projecto> listaProjecto = this.projectoService.listarProjetos();
        List<ProjectoDto> listaProjectosDto = this.projectoConverter.paraProjectoDto(listaProjecto);
        Page<ProjectoDto> listaPaginada = this.paginacaoProject.getPaginacao(listaProjectosDto, pageable);
        return listaPaginada;
    }

    @DeleteMapping("/{id_projecto}")
    @ApiOperation("Eliminar projecto")
    @ApiResponse(code = 204, message = "Projecto eliminado com sucesso.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id_projecto") Integer idProjecto) {
        log.info("ProjectoController - Eliminar projecto com id: " + idProjecto);
        this.projectoService.eliminar(idProjecto);
    }

    @GetMapping("/{id_projecto}")
    @ApiOperation("Buscar projecto encontrado.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Projecto encontrado."),
            @ApiResponse(code = 404, message = "Projecto não encontrado.")
    })
    @ResponseStatus(HttpStatus.FOUND)
    public ProjectoDto buscarProjecto(@PathVariable("id_projecto") Integer idProjecto) {
        log.info("ProjectoController - Buscar projecto com id: " + idProjecto);
        return this.projectoService.buscarProjecto(idProjecto);

    }

}