package br.com.jael.springcurso.springbootcurso.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jael.springcurso.springbootcurso.model.entities.Cliente;
import br.com.jael.springcurso.springbootcurso.model.entities.Pedido;

public interface PedidosRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

}
