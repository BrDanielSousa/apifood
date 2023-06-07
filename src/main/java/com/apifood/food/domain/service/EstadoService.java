package com.apifood.food.domain.service;

import com.apifood.food.domain.exception.EntidadeEmUsoException;
import com.apifood.food.domain.exception.EntidadeNaoEncontradaException;
import com.apifood.food.domain.model.Cozinha;
import com.apifood.food.domain.model.Estado;
import com.apifood.food.domain.model.Restaurante;
import com.apifood.food.domain.repository.CozinhaRepository;
import com.apifood.food.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {
    @Autowired
    private EstadoRepository estadoRepository;

    public Estado salvar(Estado estado){
        return estadoRepository.save(estado);
    }

    public Estado atualizar(Estado estado, Long estadoId){

        Estado estadoAtual = estadoRepository.findById(estadoId)
                .orElseThrow(()-> new EntidadeNaoEncontradaException(String.format("Nao existe estado com esse codigo %d", estadoId)));

        BeanUtils.copyProperties(estado, estadoAtual, "id");
        /**BeanUtils.copyProperties() é um método da biblioteca Apache Commons BeanUtils que permite copiar os valores das
         * propriedades de um objeto de origem para um objeto de destino. O método faz uma cópia dos valores de todas as
         * propriedades correspondentes da origem para o destino.**/

       salvar(estado);

        return salvar(estado);
    }

    public void excluir(Long estadoId){
        try {
            estadoRepository.deleteById(estadoId);
        }catch(EmptyResultDataAccessException e){
            /**EmptyResultDataAccessException é uma exceção lançada pela camada de persistência de dados quando uma consulta SQL
             * resulta em zero resultados. Isso geralmente ocorre quando você está tentando buscar um objeto em um banco de dados
             * e a consulta não retorna nenhum resultado. Por exemplo, se você estiver buscando um usuário com um determinado ID
             * em um banco de dados e esse ID não existir, a consulta retornará zero resultados.**/
            throw new EntidadeNaoEncontradaException(String.format("Nao existe um cadastro de estado com esse codigo %d", estadoId));

        }catch(DataIntegrityViolationException e) {
            /**
             DataIntegrityViolationException é uma exceção lançada pela camada de persistência de dados quando ocorre uma violação
             de integridade dos dados. Isso significa que uma operação que você está tentando executar, como inserir, atualizar ou
             excluir dados em um banco de dados, viola uma restrição de integridade definida no banco de dados, como uma restrição
             de chave primária, chave estrangeira, ou restrição de unicidade.**/
            throw new EntidadeEmUsoException(String.format("Estado de codigo %d nao podera ser excluida pois esta em uso", estadoId));
        }
    }
}
