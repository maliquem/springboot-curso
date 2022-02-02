package br.com.jael.springcurso.springbootcurso.service;

import org.springframework.stereotype.Service;

import br.com.jael.springcurso.springbootcurso.model.Cliente;
import br.com.jael.springcurso.springbootcurso.repository.ClientesRepository;

@Service
public class ClientesService {

    private ClientesRepository repository;

    public ClientesService(ClientesRepository repository) {
        this.repository = repository;
    }

    public void salvarCliente(Cliente cliente) {
        validarCliente(cliente);
        this.repository.persistir(cliente);
    }

    public void validarCliente(Cliente cliente) {

    }

}
