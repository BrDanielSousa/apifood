package com.apifood.food.domain.repository;


import com.apifood.food.domain.model.Cozinha;

import java.util.List;

public interface CozinhaRepository {

    List<Cozinha> listaCozinha();
    Cozinha buscarPeloId(Long id);
    Cozinha adicionar(Cozinha cozinha);
    void remover(Long id);
}
