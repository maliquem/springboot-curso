package br.com.jael.springcurso.springbootcurso.service;

import br.com.jael.springcurso.springbootcurso.domain.entities.Pedido;
import br.com.jael.springcurso.springbootcurso.domain.enums.StatusPedido;
import br.com.jael.springcurso.springbootcurso.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizarStatus(Integer id, StatusPedido statusPedido);

}
