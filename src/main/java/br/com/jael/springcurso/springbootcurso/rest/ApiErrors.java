package br.com.jael.springcurso.springbootcurso.rest;

import lombok.Getter;

import java.util.List;

public class ApiErrors {

    @Getter
    private final List<String> errors;

    public ApiErrors(String mensagemErro) {
        this.errors = List.of(mensagemErro);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }
}
