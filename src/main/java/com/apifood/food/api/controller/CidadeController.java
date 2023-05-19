package com.apifood.food.api.controller;

import com.apifood.food.domain.exception.EntidadeEmUsoException;
import com.apifood.food.domain.exception.EntidadeNaoEncontradaException;
import com.apifood.food.domain.model.Cidade;
import com.apifood.food.domain.repository.CidadeRepository;
import com.apifood.food.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {


    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    //(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}), especificando o tipo de objeto que retornata
    public List<Cidade> listar(){
        return cidadeRepository.listaCidade();
    }

    //@ResponseStatus(HttpStatus.OK)
    //A anotação @ResponseStatus é usada no Spring Boot para definir o código de status HTTP que deve ser retornado
    // em caso de sucesso ao manipular uma determinada exceção.
    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> buscar(@PathVariable("cidadeId") Long id){
        Cidade cidade = cidadeRepository.buscarPeloId(id);

        if (cidade != null){
            return ResponseEntity.ok(cidade);
        }

        return ResponseEntity.notFound().build();
        //Caso nao tenha aquele id ele retonara esse status
    }

    @PostMapping
    public ResponseEntity<Cidade> adicionar(@RequestBody Cidade cidade){
        try {
            Cidade cidadeCadastrado = cidadeService.salvar(cidade);

            return ResponseEntity.status(HttpStatus.CREATED).body(cidadeCadastrado);

        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().build();
            /**
             O método ResponseEntity.badRequest().build() é usado para criar uma resposta HTTP de status 400 Bad Request
             (Solicitação Inválida) em um serviço web. Quando ocorre um erro de solicitação inválida, como dados ausentes
             ou incorretos, você pode retornar essa resposta para indicar ao cliente que a solicitação não pôde ser processada
             devido a um problema com os dados fornecidos.**/
        }
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable Long cidadeId,
                                       @RequestBody Cidade cidade){
        try {
            Cidade cidadeAtual = cidadeRepository.buscarPeloId(cidadeId);

            if (cidadeAtual == null){
                return ResponseEntity.notFound().build();
            }

            Cidade cidadeAtualizado = cidadeService.atualizar(cidadeAtual, cidade);
            return ResponseEntity.ok(cidadeAtualizado);

        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Cidade> remover(@PathVariable Long cidadeId){
        try {
            cidadeService.excluir(cidadeId);
            return ResponseEntity.noContent().build();
            /**ResponseEntity.noContent().build() é usado para criar uma resposta HTTP com o status 204 No Content.O status 204 é retornado
             * pelo servidor quando uma solicitação é processada com sucesso, mas não há conteúdo a ser retornado. Isso geralmente é usado
             * quando uma operação de exclusão ou atualização é bem-sucedida e não há conteúdo relevante para retornar.**/

        }catch(EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
            /**ResponseEntity.notFound().build() é usado para criar uma resposta HTTP com o status 404 Not Found.O status 404 é retornado pelo
             *  servidor quando a solicitação não pôde ser atendida porque o recurso solicitado não foi encontrado. Isso geralmente é usado
             *  quando um cliente faz uma solicitação para um recurso que não existe.**/

        }catch(EntidadeEmUsoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
