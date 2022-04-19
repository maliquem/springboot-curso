package br.com.jael.springcurso.springbootcurso.rest.controller;

import br.com.jael.springcurso.springbootcurso.domain.entities.Cliente;
import br.com.jael.springcurso.springbootcurso.domain.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClientesRepository clientesRepository;

    @GetMapping("/{id}")
    //@ResponseStatus(NO_CONTENT)
    public Cliente getClienteById(@PathVariable Integer id) {
        return clientesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cliente postProduto(@RequestBody @Valid Cliente cliente) {
        return clientesRepository.save(cliente);
    }

    @PutMapping("/{id}")
    //@ResponseStatus(NO_CONTENT)
    public void putCliente(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
        clientesRepository.findById(id).map(p -> {
            cliente.setId(p.getId());
            clientesRepository.save(cliente);
            return cliente;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado."));
    }

    @DeleteMapping("/{id}")
    //@ResponseStatus(NO_CONTENT)
    public void deleteCliente(@PathVariable Integer id) {
        clientesRepository.findById(id).map(p -> {
            clientesRepository.delete(p);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado."));
    }

    @GetMapping
    public List<Cliente> findCliente(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
        Example<Cliente> example = Example.of(filtro, matcher);
        return clientesRepository.findAll(example);
    }

}
