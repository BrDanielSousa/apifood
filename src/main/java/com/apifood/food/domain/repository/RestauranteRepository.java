package com.apifood.food.domain.repository;

import com.apifood.food.domain.model.Cozinha;
import com.apifood.food.domain.model.Restaurante;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listaRestaurante();
    Restaurante buscarPeloId(Long id);
    Restaurante adicionar(Restaurante restaurante);
    void remover(Long id);
}
