package com.apifood.food.domain.service;

import com.apifood.food.domain.exception.EntidadeEmUsoException;
import com.apifood.food.domain.exception.EntidadeNaoEncontradaException;
import com.apifood.food.domain.model.Cidade;
import com.apifood.food.domain.model.Cozinha;
import com.apifood.food.domain.model.Estado;
import com.apifood.food.domain.model.Restaurante;
import com.apifood.food.domain.repository.CidadeRepository;
import com.apifood.food.domain.repository.CozinhaRepository;
import com.apifood.food.domain.repository.EstadoRepository;
import com.apifood.food.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Transactional
    public Cidade salvar(Cidade cidade){

        Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Nao existe estado com esse codigo %d", estadoId)));

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    @Transactional
    public Cidade atualizar(Cidade cidadeAtual, Cidade cidade){

        Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoRepository.findById(estadoId)
                .orElseThrow(()-> new EntidadeNaoEncontradaException(String.format("Nao existe estado com esse codigo %d", estadoId)));

        cidade.setEstado(estado);

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        return cidadeRepository.save(cidadeAtual);
    }

    @Transactional
    public void excluir(Long cidadeId){
        try {
            cidadeRepository.deleteById(cidadeId);
        }catch(EmptyResultDataAccessException e){
            /**EmptyResultDataAccessException é uma exceção lançada pela camada de persistência de dados quando uma consulta SQL
             * resulta em zero resultados. Isso geralmente ocorre quando você está tentando buscar um objeto em um banco de dados
             * e a consulta não retorna nenhum resultado. Por exemplo, se você estiver buscando um usuário com um determinado ID
             * em um banco de dados e esse ID não existir, a consulta retornará zero resultados.**/
            throw new EntidadeNaoEncontradaException(String.format("Nao existe um cadastro de cozinha com esse codigo %d", cidadeId));

        }catch(DataIntegrityViolationException e) {
            /**
             DataIntegrityViolationException é uma exceção lançada pela camada de persistência de dados quando ocorre uma violação
             de integridade dos dados. Isso significa que uma operação que você está tentando executar, como inserir, atualizar ou
             excluir dados em um banco de dados, viola uma restrição de integridade definida no banco de dados, como uma restrição
             de chave primária, chave estrangeira, ou restrição de unicidade.**/
            throw new EntidadeEmUsoException(String.format("Cozinha de codigo %d nao podera ser excluida pois esta em uso", cidadeId));
        }
    }
}
