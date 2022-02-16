package br.com.jael.springcurso.springbootcurso.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
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

import br.com.jael.springcurso.springbootcurso.model.entities.Produto;
import br.com.jael.springcurso.springbootcurso.model.repository.ProdutosRepository;

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
    public Produto postProduto(@RequestBody Produto produto) {
        return produtosRepository.save(produto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void putProduto(@PathVariable Integer id, @RequestBody Produto produto) {
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
