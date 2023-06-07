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

}
