package com.apifood.food.infrastructure.repository;

import com.apifood.food.domain.model.Cozinha;
import com.apifood.food.domain.repository.CozinhaRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {

    @PersistenceContext
    //A anotação @PersistenceContext é usada em um aplicativo Spring Boot para injetar uma instância de
    // EntityManagerem um bean gerenciado pelo Spring. O EntityManager é uma interface que permite que o
    // aplicativo gerencie a persistênciade entidades JPA (Java Persistence API).
    //Quando a anotação @PersistenceContext é colocada em um campo, setter ou método de construtor em um bean gerenciado
    // pelo Spring, o Spring detecta a anotação e fornece uma instância de EntityManager que pode ser usada pelo bean para
    // realizar operações de banco de dados.
    //Essa anotação é geralmente usada em conjunto com o JPA e Hibernate para
    // simplificar a configuração e o gerenciamento do acesso ao banco de dados em um
    // aplicativo Spring Boot.
    EntityManager manager;

    @Override
    public List<Cozinha> listaCozinha() {
        return manager.createQuery("From Cozinha", Cozinha.class).getResultList();
    }

    @Override
    public Cozinha buscarPeloId(Long id) {
        return manager.find(Cozinha.class, id);
    }

    @Transactional
    /**A anotação @Transactional é uma anotação fornecida pelo Spring Framework que indica que um método deve ser executado dentro do
     * escopo de uma transação.Uma transação é uma unidade de trabalho que consiste em uma ou mais operações de banco de dados que
     * devem ser executadas em conjunto como uma única unidade lógica. Essas operações podem incluir inserção, atualização ou exclusão
     * de dados.**/
    @Override
    public Cozinha adicionar(Cozinha cozinha) {
        return manager.merge(cozinha);
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Cozinha cozinha = buscarPeloId(id);
        if (cozinha == null){
            throw new EmptyResultDataAccessException(1);
        }
        manager.remove(cozinha);
    }
}
