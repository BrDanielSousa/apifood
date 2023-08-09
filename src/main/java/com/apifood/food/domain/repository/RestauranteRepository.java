package com.apifood.food.domain.repository;

import com.apifood.food.domain.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries {

    List<Restaurante> find(String nome, BigDecimal taxaFreteInicio, BigDecimal taxaFreteFinal);

    List<Restaurante> findByTaxaFreteBetween(BigDecimal taxa_inicial, BigDecimal taxa_final);
//    findByTaxaFreteBetween: Esse é o nome do método de consulta. O prefixo findBy é uma convenção do Spring Data JPA para indicar
//    que o método é uma consulta por atributo. Nesse caso, o atributo é taxaFrete. O sufixo Between indica que a consulta buscará
//    objetos cujo atributo taxaFrete esteja dentro de um intervalo.

//    @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
//    List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha);

    List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);

    List<Restaurante> findTop2ByNomeContaining(String nome);

    int countByCozinhaId(Long cozinha);


}
