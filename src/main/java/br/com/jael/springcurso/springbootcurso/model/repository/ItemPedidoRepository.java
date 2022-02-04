package br.com.jael.springcurso.springbootcurso.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jael.springcurso.springbootcurso.model.entities.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
