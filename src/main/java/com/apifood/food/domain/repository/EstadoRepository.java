package com.apifood.food.domain.repository;

import com.apifood.food.domain.model.Cozinha;
import com.apifood.food.domain.model.Estado;

import java.util.List;

public interface EstadoRepository {

    List<Estado> listaEstado();
    Estado buscarPeloId(Long id);
    Estado adicionar(Estado estado);
    void remover(Long id);
}
