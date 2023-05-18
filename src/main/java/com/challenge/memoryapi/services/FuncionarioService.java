package com.challenge.memoryapi.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.challenge.memoryapi.converters.FuncionarioConverter;
import com.challenge.memoryapi.dto.FuncionarioDto;
import com.challenge.memoryapi.exceptions.FuncionarioException;
import com.challenge.memoryapi.models.Funcionario;
import com.challenge.memoryapi.repositories.FuncionarioRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioConverter funcionarioConverter;

    public Funcionario salvar(FuncionarioDto funcionarioDto) {
        log.info("FuncionarioService - Salvando funcionário.");
        try {

            Funcionario funcionario = this.funcionarioConverter.paraFuncionario(funcionarioDto);
            if (funcionario.getId() == null) {
                funcionario.setDataAdmicao(LocalDate.now());
            }
            return this.funcionarioRepository.save(funcionario);
        } catch (Exception e) {
            throw new FuncionarioException("Erro ao castrar funcionário. "
                    + "CPF já existe.");
        }

    }

    public Page<Funcionario> listarTodos(Pageable pageble) {
        log.info("FuncionarioService - Listando os funcionários.");
        return this.funcionarioRepository.findAll(pageble);
    }

    public Funcionario getFuncionario(Integer idFuncionario) {
        log.info("FuncionarioService - Buscando funcionário com id: " + idFuncionario);
        return this.funcionarioRepository
                .findById(idFuncionario)
                .orElseThrow(() -> new FuncionarioException(
                        "Funcionário não encontrado. Id invalido: " + idFuncionario));
    }

    public void eliminar(Integer idFuncionario) {
        log.info("FuncionarioService - Eliminando funcionário com id: " + idFuncionario);
        Funcionario funcionario = this.getFuncionario(idFuncionario);
        this.funcionarioRepository.delete(funcionario);
    }

    public Funcionario atualizar(Funcionario funcionario, Integer idFuncionario) {
        log.info("FuncionarioService - Atualizando funcionário com id: " + idFuncionario);

        Funcionario funcionarioSalvo = this.getFuncionario(idFuncionario);
        funcionario.setDataAdmicao(funcionarioSalvo.getDataAdmicao());
        funcionario.setId(funcionarioSalvo.getId());

        return this.funcionarioRepository.save(funcionario);
    }
}
