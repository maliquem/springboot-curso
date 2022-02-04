package br.com.jael.springcurso.springbootcurso.model.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.jael.springcurso.springbootcurso.model.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    public List<Cliente> findByNameLike(String parteNome);

}
