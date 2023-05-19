package com.apifood.food.infrastructure.repository;

import com.apifood.food.domain.model.Cozinha;
import com.apifood.food.domain.model.Estado;
import com.apifood.food.domain.repository.EstadoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

    @PersistenceContext
    EntityManager manager;

    @Override
    public List<Estado> listaEstado() {
        return manager.createQuery("From Estado", Estado.class).getResultList();
    }

    @Override
    public Estado buscarPeloId(Long id) {
        return manager.find(Estado.class, id);
    }

    @Transactional
    @Override
    public Estado adicionar(Estado estado) {
        return manager.merge(estado);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Estado estado = buscarPeloId(id);
        if (estado == null){
            throw new EmptyResultDataAccessException(1);
        }
        manager.remove(estado);
    }
}
