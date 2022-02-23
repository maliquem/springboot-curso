package br.com.jael.springcurso.springbootcurso.domain.repository;

import br.com.jael.springcurso.springbootcurso.domain.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientesRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNameLike(String parteNome);

}
