package com.apifood.food.infrastructure.repository;

import com.apifood.food.domain.model.Cidade;
import com.apifood.food.domain.model.Cozinha;
import com.apifood.food.domain.repository.CidadeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    EntityManager manager;

    @Override
    public List<Cidade> listaCidade() {
        return manager.createQuery("From Cidade", Cidade.class).getResultList();
    }

    @Override
    public Cidade buscarPeloId(Long id) {
        return manager.find(Cidade.class, id);
    }

    @Transactional
    @Override
    public Cidade adicionar(Cidade cidade) {
        return manager.merge(cidade);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Cidade cidade = buscarPeloId(id);
        if (cidade == null){
            throw new EmptyResultDataAccessException(1);
        }
        manager.remove(cidade);
    }
}
