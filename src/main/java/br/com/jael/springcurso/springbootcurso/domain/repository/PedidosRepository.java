package br.com.jael.springcurso.springbootcurso.domain.repository;

import br.com.jael.springcurso.springbootcurso.domain.entities.Cliente;
import br.com.jael.springcurso.springbootcurso.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

}
