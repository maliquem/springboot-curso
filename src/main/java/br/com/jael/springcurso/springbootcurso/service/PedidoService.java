package br.com.jael.springcurso.springbootcurso.service;

import br.com.jael.springcurso.springbootcurso.domain.entities.Pedido;
import br.com.jael.springcurso.springbootcurso.rest.dto.PedidoDTO;

public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

}
