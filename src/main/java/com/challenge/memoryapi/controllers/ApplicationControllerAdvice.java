package com.challenge.memoryapi.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.challenge.memoryapi.exceptions.ColaboradorException;
import com.challenge.memoryapi.exceptions.DadosInvalidoException;
import com.challenge.memoryapi.exceptions.FuncionarioException;
import com.challenge.memoryapi.exceptions.ProjectoNotFoundException;
import com.challenge.memoryapi.utils.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

	private String mensagemErro;

	@ResponseBody
	@ExceptionHandler(DadosInvalidoException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors dadosInvalidoExceptionHandle2(DadosInvalidoException ex) {
		this.mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ResponseBody
	@ExceptionHandler(ColaboradorException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors colaboradorExceptionHandle(ColaboradorException ex) {
		this.mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ResponseBody
	@ExceptionHandler(FuncionarioException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors funcionarioExceptionHandle(FuncionarioException ex) {
		this.mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ResponseBody
	@ExceptionHandler(ProjectoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrors projectoNotFoundExceptionHandle(ProjectoNotFoundException ex) {
		this.mensagemErro = ex.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrors validateFieldsHandle(MethodArgumentNotValidException ex) {
		List<String> erros = ex.getBindingResult()
				.getAllErrors()
				.stream()
				.map(erro -> erro.getDefaultMessage()).collect(Collectors.toList());

		return new ApiErrors(erros);
	}
}
