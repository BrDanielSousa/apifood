package com.apifood.food.domain.repository;

import com.apifood.food.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {
    List<Restaurante> find(String nome, BigDecimal taxaFreteInicio, BigDecimal taxaFreteFinal);
}
