package br.com.jael.springcurso.springbootcurso.domain.repository;

import br.com.jael.springcurso.springbootcurso.domain.entities.Cliente;
import br.com.jael.springcurso.springbootcurso.domain.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PedidosRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente(Cliente cliente);

    @Query(" SELECT p FROM Pedido p LEFT JOIN FETCH p.itens WHERE p.id = :id ")
    Optional<Pedido> findByIdFetchItens(Integer id);

}
