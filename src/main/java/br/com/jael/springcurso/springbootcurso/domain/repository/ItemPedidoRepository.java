package br.com.jael.springcurso.springbootcurso.domain.repository;

import br.com.jael.springcurso.springbootcurso.domain.entities.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
