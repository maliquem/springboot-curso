package br.com.jael.springcurso.springbootcurso.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;

import br.com.jael.springcurso.springbootcurso.model.entities.Cliente;
import br.com.jael.springcurso.springbootcurso.model.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public Cliente getClienteById(@PathVariable Integer id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cliente postProduto(@RequestBody Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void putCliente(@PathVariable Integer id, @RequestBody Cliente cliente) {
        clienteRepository.findById(id).map(p -> {
            cliente.setId(p.getId());
            clienteRepository.save(cliente);
            return cliente;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado."));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCliente(@PathVariable Integer id) {
        clienteRepository.findById(id).map(p -> {
            clienteRepository.delete(p);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado."));
    }

    @GetMapping
    public List<Cliente> findCliente(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
        Example<Cliente> example = Example.of(filtro, matcher);
        return clienteRepository.findAll(example);
    }

}
