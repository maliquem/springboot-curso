package br.com.jael.springcurso.springbootcurso.rest.controller;

import br.com.jael.springcurso.springbootcurso.domain.entities.Produto;
import br.com.jael.springcurso.springbootcurso.domain.repository.ProdutosRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
    @ApiOperation("Obter detalhes de um produto.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto Encontrado."),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado.")
    })
    public Produto getProdutoById(@PathVariable Integer id) {
        return produtosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado."));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Salvar um novo produto.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto Salvo com sucesso."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    public Produto postProduto(@RequestBody @Valid Produto produto) {
        return produtosRepository.save(produto);
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar um produto existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto Atualizado."),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado.")
    })
    public void putProduto(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
        produtosRepository.findById(id).map(p -> {
            produto.setId(p.getId());
            produtosRepository.save(produto);
            return produto;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado."));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletar um produto existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto deletado."),
            @ApiResponse(code = 404, message = "Produto não encontrado para o ID informado.")
    })
    public void deleteProduto(@PathVariable Integer id) {
        produtosRepository.findById(id).map(p -> {
            produtosRepository.delete(p);
            return Void.TYPE;
        }).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Produto não encontrado."));
    }

    @GetMapping
    @ApiOperation("Obter detalhes de produto, de que contem a string")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produtos encontrados."),
            @ApiResponse(code = 404, message = "Não possui nenhum produto, que contem a string passada.")
    })
    public List<Produto> findProduto(Produto filtro) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);
        Example<Produto> example = Example.of(filtro, matcher);
        return produtosRepository.findAll(example);
    }

}
