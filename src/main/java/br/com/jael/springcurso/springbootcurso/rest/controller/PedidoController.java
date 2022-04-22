package br.com.jael.springcurso.springbootcurso.rest.controller;

import br.com.jael.springcurso.springbootcurso.domain.entities.ItemPedido;
import br.com.jael.springcurso.springbootcurso.domain.entities.Pedido;
import br.com.jael.springcurso.springbootcurso.domain.enums.StatusPedido;
import br.com.jael.springcurso.springbootcurso.rest.dto.AtualizacaoStatusPedidoDTO;
import br.com.jael.springcurso.springbootcurso.rest.dto.InformacaoItemPedidoDTO;
import br.com.jael.springcurso.springbootcurso.rest.dto.InformacoesPedidoDTO;
import br.com.jael.springcurso.springbootcurso.rest.dto.PedidoDTO;
import br.com.jael.springcurso.springbootcurso.service.PedidoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @ApiOperation("Criar um pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido criado com sucesso."),
            @ApiResponse(code = 400, message = "Erro de validação.")
    })
    public Integer postPedido(@RequestBody @Valid PedidoDTO dto){
        Pedido pedido = pedidoService.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("/{id}")
    @ApiOperation("Obter detalhes de um pedido.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido Encontrado."),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado.")
    })
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return pedidoService.obterPedidoCompleto(id).map(this::converterPedido).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido não encontrado."));
    }

    private InformacoesPedidoDTO converterPedido(Pedido p) {
        return InformacoesPedidoDTO.builder()
                .codigo(p.getId())
                .data(p.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .status(p.getStatus().name())
                .cpf(p.getCliente().getCpf())
                .nomeCliente(p.getCliente().getName())
                .total(p.getTotal())
                .itens(converterItemPedido(p.getItens()))
                .build();
    }

    private List<InformacaoItemPedidoDTO> converterItemPedido(List<ItemPedido> itens){
        if (CollectionUtils.isEmpty(itens)) return Collections.emptyList();

        return itens.stream().map(item -> InformacaoItemPedidoDTO
                .builder()
                .nomeProduto(item.getProduto().getName())
                .precoProduto(item.getProduto().getPreco())
                .quantidade(item.getQuantidade())
                .build()
        ).collect(Collectors.toList());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @ApiOperation("Atualizar um pedido existente")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pedido Atualizado."),
            @ApiResponse(code = 404, message = "Pedido não encontrado para o ID informado.")
    })
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizarStatus(id, StatusPedido.valueOf(novoStatus));

    }
}
