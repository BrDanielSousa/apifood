package com.apifood.food.domain.repository;

import com.apifood.food.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//A anotação @Repository é uma anotação específica do framework Spring, geralmente usada em classes de acesso
//a dados(Data Access Objects - DAOs) para indicar que a classe é responsável pelo acesso a dados e interações
//com um banco de dados.
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    //List<Cozinha> findByNomeContaining(String nome);
    //findByNomeContaining: Esse é o nome do método de consulta. O prefixo findBy é uma convenção usada pelo Spring Data JPA para
    //indicar que o método é uma consulta por atributo. Nesse caso, o atributo é nome. O sufixo Containing indica que a consulta
    //buscará objetos cujo atributo nome contenha uma correspondência parcial com o valor fornecido.

    //Optional<Cozinha> findByNome(String nome);

    //boolean existsByNome(String nome); true ou false
}
