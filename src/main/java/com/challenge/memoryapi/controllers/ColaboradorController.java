package com.challenge.memoryapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.challenge.memoryapi.converters.ColaboradorConverter;
import com.challenge.memoryapi.dto.ColaboradorDto;
import com.challenge.memoryapi.models.Colaborador;
import com.challenge.memoryapi.services.ColaboradorService;
import com.challenge.memoryapi.utils.PaginacaoColaborador;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/colaboradores")
@Api("API de colaboradores")
public class ColaboradorController {

        @Autowired
        private ColaboradorService colaboradorService;

        @Autowired
        private ColaboradorConverter colaboradorConverter;

        @Autowired
        private PaginacaoColaborador paginacaoColaborador;

        @PostMapping
        @ApiOperation("Salvar colaboradores com os seus projectos")
        @ApiResponses({
                        @ApiResponse(code = 201, message = "Colaborador salvo com sucesso."),
                        @ApiResponse(code = 404, message = "Erro ao salvar Colaborador")
        })
        @ResponseStatus(HttpStatus.CREATED)
        public List<ColaboradorDto> salvar(@RequestBody ColaboradorDto colaboradorDto) {
                log.info("ColaboradorController - Salvar colaborador com seu projecto.");
                return this.colaboradorService.salvar(colaboradorDto);
        }

        @GetMapping
        @ApiOperation("Listar todos os colaboradores.")
        @ApiResponses({
                        @ApiResponse(code = 302, message = "Colaboradoes encontrados"),
                        @ApiResponse(code = 204, message = "Colaboradores não encontrados.")
        })
        @ResponseStatus(HttpStatus.OK)
        public Page<ColaboradorDto> listarTodos(
                        @PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

                List<Colaborador> listaColaboradores = this.colaboradorService.listaColaborador();
                List<ColaboradorDto> listaColaboradoresDto = this.colaboradorConverter
                                .paraColaboradorDto(listaColaboradores);

                Page<ColaboradorDto> listaPaginada = this.paginacaoColaborador
                                .getPaginacao(listaColaboradoresDto, pageable);

                return listaPaginada;
        }

        @GetMapping("/{id_projecto}")
        @ApiOperation("Buscar colaboradores com os seus projectos.")
        @ApiResponses({
                        @ApiResponse(code = 302, message = "Colaboradoes encontrados"),
                        @ApiResponse(code = 204, message = "Colaboradores não encontrados.")
        })
        @ResponseStatus(HttpStatus.FOUND)
        public List<ColaboradorDto> getColaboradoresByProjectoIdProjecto(
                        @PathVariable("id_projecto") Integer idProjecto) {
                log.info("ColaboradorController - Buscar colaboradores com seu projecto. id_projecto: " + idProjecto);
                return this.colaboradorService.getColaboradoresByProjectoIdProjecto(idProjecto);
        }

        @GetMapping("/pesquisa")
        @ApiOperation("Buscar colaboradores pelo ano de admição.")
        @ApiResponses({
                        @ApiResponse(code = 302, message = "Colaboradoes encontrados"),
                        @ApiResponse(code = 204, message = "Colaboradores não encontrados.")
        })
        @ResponseStatus(HttpStatus.FOUND)
        public Page<ColaboradorDto> getColaboradoresByFuncionarioAnoAdimicao(
                        @RequestParam(value = "ano", defaultValue = "2023", required = false) Integer ano,
                        @PageableDefault(page = 0, size = 4, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
                log.info("ColaboradorController - Buscar colaboradores por ano: " + ano);

                List<Colaborador> listaColaboradores = this.colaboradorService
                                .getColaboradoresByFuncionarioAnoAdimicao(ano);
                List<ColaboradorDto> listaColaboradoresDto = this.colaboradorConverter
                                .paraColaboradorDto(listaColaboradores);
                Page<ColaboradorDto> listaPaginada = this.paginacaoColaborador
                                .getPaginacao(listaColaboradoresDto, pageable);
                return listaPaginada;

        }

        @DeleteMapping("/{id_projecto}")
        @ApiOperation("Eliminar colaboradores por projecto.")
        @ApiResponse(code = 204, message = "Colaboradores eliminados com sucesso.")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void eliminarColaboradoresByProjecto(@PathVariable("id_projecto") Integer idProjecto) {
                log.info("ColaboradorController - Eliminar colaboradores com seu projecto. id_projecto: " + idProjecto);
                this.colaboradorService.eliminarColaboradoresByProjecto(idProjecto);
        }

        @PatchMapping("/{id_colaborador}")
        @ApiOperation("Atualizar colaborador com seu projecto.")
        @ApiResponse(code = 201, message = "Colaboradores eliminados com sucesso.")
        @ResponseStatus(HttpStatus.CREATED)
        public List<ColaboradorDto> atualizar(@RequestBody ColaboradorDto colaboradorDto,
                        @PathVariable("id_colaborador") Integer idColaborador) {
                log.info("ColaboradorController - Atualizar colaborador com seu projecto. idColaborador: "
                                + idColaborador);
                return this.colaboradorService.atualizar(colaboradorDto, idColaborador);
        }

}
