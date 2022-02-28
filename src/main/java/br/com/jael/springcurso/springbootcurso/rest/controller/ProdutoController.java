package br.com.jael.springcurso.springbootcurso.rest.controller;

import br.com.jael.springcurso.springbootcurso.domain.entities.Produto;
import br.com.jael.springcurso.springbootcurso.domain.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    ProdutosRepository produtosRepository;

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable Integer id) {
        return produtosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado."));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto postProduto(@RequestBody @Valid Produto produto) {
        return produtosRepository.save(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void putProduto(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
        produtosRepository.findById(id).map(p -> {
            produto.setId(p.getId());
            produtosRepository.save(produto);
            return produto;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado."));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteProduto(@PathVariable Integer id) {
        produtosRepository.findById(id).map(p -> {
            produtosRepository.delete(p);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado."));
    }

    @GetMapping
    public List<Produto> findProduto(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
        Example<Produto> example = Example.of(filtro, matcher);
        return produtosRepository.findAll(example);
    }

}
