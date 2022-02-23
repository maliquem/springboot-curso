package br.com.jael.springcurso.springbootcurso.domain.repository;

import br.com.jael.springcurso.springbootcurso.domain.entities.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutosRepository extends JpaRepository<Produto, Integer> {

}
