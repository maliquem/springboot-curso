package br.com.jael.springcurso.springbootcurso.model.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.jael.springcurso.springbootcurso.model.entities.Cliente;

@Repository
public class ClienteRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente) {
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void delete(int id) {
        Cliente cliente = entityManager.find(Cliente.class, id);
        delete(cliente);
    }

    @Transactional
    public void delete(Cliente cliente) {
        if (!entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNomes(String name) {
        String jpql = "SELECT c FROM Cliente c WHERE c.name LIKE :name";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Transactional
    public List<Cliente> obterTodos() {
        return entityManager.createQuery("FROM Cliente", Cliente.class).getResultList();
    }

}
