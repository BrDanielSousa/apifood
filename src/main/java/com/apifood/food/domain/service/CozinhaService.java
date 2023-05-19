package com.apifood.food.domain.service;

import com.apifood.food.domain.exception.EntidadeEmUsoException;
import com.apifood.food.domain.exception.EntidadeNaoEncontradaException;
import com.apifood.food.domain.model.Cozinha;
import com.apifood.food.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha){
        return cozinhaRepository.adicionar(cozinha);
    }

    public void excluir(Long cozinhaId){
        try {
            cozinhaRepository.remover(cozinhaId);
        }catch(EmptyResultDataAccessException e){
            /**EmptyResultDataAccessException é uma exceção lançada pela camada de persistência de dados quando uma consulta SQL
             * resulta em zero resultados. Isso geralmente ocorre quando você está tentando buscar um objeto em um banco de dados
             * e a consulta não retorna nenhum resultado. Por exemplo, se você estiver buscando um usuário com um determinado ID
             * em um banco de dados e esse ID não existir, a consulta retornará zero resultados.**/
            throw new EntidadeNaoEncontradaException(String.format("Nao existe um cadastro de cozinha com esse codigo %d", cozinhaId));

        }catch(DataIntegrityViolationException e) {
            /**
             DataIntegrityViolationException é uma exceção lançada pela camada de persistência de dados quando ocorre uma violação
             de integridade dos dados. Isso significa que uma operação que você está tentando executar, como inserir, atualizar ou
             excluir dados em um banco de dados, viola uma restrição de integridade definida no banco de dados, como uma restrição
             de chave primária, chave estrangeira, ou restrição de unicidade.**/
            throw new EntidadeEmUsoException(String.format("Cozinha de codigo %d nao podera ser excluida pois esta em uso", cozinhaId));
        }
    }
}
