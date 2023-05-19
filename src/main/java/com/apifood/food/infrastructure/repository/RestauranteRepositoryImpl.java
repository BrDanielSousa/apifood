package com.apifood.food.infrastructure.repository;

import com.apifood.food.domain.model.Cozinha;
import com.apifood.food.domain.model.Restaurante;
import com.apifood.food.domain.repository.RestauranteRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

    @PersistenceContext
    EntityManager manager;

    @Override
    public List<Restaurante> listaRestaurante() {
        return manager.createQuery("From Restaurante", Restaurante.class).getResultList();
    }

    @Override
    public Restaurante buscarPeloId(Long id) {
        return manager.find(Restaurante.class, id);
    }

    @Transactional
    @Override
    public Restaurante adicionar(Restaurante restaurante) {
        return manager.merge(restaurante);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Restaurante restaurante = buscarPeloId(id);
        if (restaurante == null){
            throw new EmptyResultDataAccessException(1);
        }
        manager.remove(restaurante);
    }
}
