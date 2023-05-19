package com.apifood.food.domain.repository;

import com.apifood.food.domain.model.Cidade;

import java.util.List;

public interface CidadeRepository {

    List<Cidade> listaCidade();
    Cidade buscarPeloId(Long id);
    Cidade adicionar(Cidade cidade);

    void remover(Long id);
}
