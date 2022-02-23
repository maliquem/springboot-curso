package br.com.jael.springcurso.springbootcurso.service.impl;

import br.com.jael.springcurso.springbootcurso.domain.entities.Cliente;
import br.com.jael.springcurso.springbootcurso.domain.entities.ItemPedido;
import br.com.jael.springcurso.springbootcurso.domain.entities.Pedido;
import br.com.jael.springcurso.springbootcurso.domain.entities.Produto;
import br.com.jael.springcurso.springbootcurso.domain.enums.StatusPedido;
import br.com.jael.springcurso.springbootcurso.domain.repository.ClientesRepository;
import br.com.jael.springcurso.springbootcurso.domain.repository.ItemPedidoRepository;
import br.com.jael.springcurso.springbootcurso.domain.repository.PedidosRepository;
import br.com.jael.springcurso.springbootcurso.domain.repository.ProdutosRepository;
import br.com.jael.springcurso.springbootcurso.exception.PedidoNaoEncontradoException;
import br.com.jael.springcurso.springbootcurso.exception.RegraNegocioException;
import br.com.jael.springcurso.springbootcurso.rest.dto.ItemPedidoDTO;
import br.com.jael.springcurso.springbootcurso.rest.dto.PedidoDTO;
import br.com.jael.springcurso.springbootcurso.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidosRepository pedidosRepository;
    private final ClientesRepository clientesRepository;
    private final ProdutosRepository produtosRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = clientesRepository.findById(idCliente).orElseThrow(() -> new RegraNegocioException("Código de Cliente inválido"));
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
        pedidosRepository.save(pedido);
        itemPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidosRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizarStatus(Integer id, StatusPedido statusPedido) {
        pedidosRepository.findById(id).map(p -> {
            p.setStatus(statusPedido);
            return pedidosRepository.save(p);
        }).orElseThrow(PedidoNaoEncontradoException::new);

    }


    private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens");
        }

        return itens.stream().map(dto -> {
            Integer idProduto = dto.getProduto();
            Produto produto = produtosRepository.findById(idProduto).orElseThrow(() -> new RegraNegocioException("Código de Produto inválido"));

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setQuantidade(dto.getQuantidade());
            itemPedido.setPedido(pedido);
            itemPedido.setProduto(produto);
            return itemPedido;
        }).collect(Collectors.toList());
    }
}
