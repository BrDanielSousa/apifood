package com.apifood.food.infrastructure.repository;

import com.apifood.food.domain.model.Estado;
import com.apifood.food.domain.model.FormaPagamento;
import com.apifood.food.domain.repository.FormaPagamentoRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

    @PersistenceContext
    EntityManager manager;

    @Override
    public List<FormaPagamento> listaFormaPagamento() {
        return manager.createQuery("From FormaPagamento", FormaPagamento.class).getResultList();
    }

    @Override
    public FormaPagamento buscarPeloId(Long id) {
        return manager.find(FormaPagamento.class, id);
    }

    @Transactional
    @Override
    public FormaPagamento adicionar(FormaPagamento formaPagamento) {
        return manager.merge(formaPagamento);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        FormaPagamento formaPagamento = buscarPeloId(id);
        if (formaPagamento == null){
            throw new EmptyResultDataAccessException(1);
        }
        manager.remove(formaPagamento);
    }
}
