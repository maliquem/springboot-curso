package br.com.jael.springcurso.springbootcurso.rest.controller;

import br.com.jael.springcurso.springbootcurso.domain.entities.Cliente;
import br.com.jael.springcurso.springbootcurso.domain.repository.ClientesRepository;
import io.swagger.annotations.*;
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
@Api("API Clientes")
public class ClienteController {

    @Autowired
    private ClientesRepository clientesRepository;

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um cliente.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente Encontrado."),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
    public Cliente getClienteById(@PathVariable Integer id) {
        return clientesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Cliente não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Salva um novo cliente.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cliente Salvo com sucesso."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    public Cliente postCliente(@RequestBody @Valid Cliente cliente) {
        return clientesRepository.save(cliente);
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar um cliente existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente Atualizado."),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
    public void putCliente(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
        clientesRepository.findById(id).map(p -> {
            cliente.setId(p.getId());
            clientesRepository.save(cliente);
            return cliente;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado."));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletar um cliente existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cliente deletado."),
            @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.")
    })
    public void deleteCliente(@PathVariable Integer id) {
        clientesRepository.findById(id).map(p -> {
            clientesRepository.delete(p);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Cliente não encontrado."));
    }

    @GetMapping
    @ApiOperation("Obter detalhes de clientes, de que contem a string")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Clientes encontrados."),
            @ApiResponse(code = 404, message = "Não possui nenhum cliente, que contem a string passada.")
    })
    public List<Cliente> findCliente(Cliente filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
        Example<Cliente> example = Example.of(filtro, matcher);
        return clientesRepository.findAll(example);
    }

}
