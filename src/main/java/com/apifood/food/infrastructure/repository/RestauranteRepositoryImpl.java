package com.apifood.food.infrastructure.repository;

import com.apifood.food.domain.model.Restaurante;
import com.apifood.food.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicio, BigDecimal taxaFreteFinal){
        // Obtém o construtor de critérios para criar as consultas
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        // Cria um critério de consulta para a entidade Restaurante, indicando que o resultado será uma lista de Restaurante.
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

        // Define a raiz da consulta para a entidade Restaurante.
        Root<Restaurante> root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if (StringUtils.hasText(nome)){
            // Cria um predicado que representa uma condição WHERE, buscando registros cujo nome contenha o valor da variável 'nome'.
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if (taxaFreteInicio != null){
            // Cria um predicado para buscar registros com a taxa de frete maior ou igual à variável 'taxaFreteInicio'.
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicio));
        }

        if (taxaFreteInicio != null){
            // Cria um predicado para buscar registros com a taxa de frete menor ou igual à variável 'taxaFreteFinal'.
            predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        // Adiciona o predicado de busca por nome à cláusula WHERE da consulta
        criteria.where(predicates.toArray(new Predicate[0]));

        // Cria uma consulta tipada usando os critérios definidos
        TypedQuery<Restaurante> query = manager.createQuery(criteria);

        // Executa a consulta e retorna a lista de resultados encontrados
        return query.getResultList();
    }
}
