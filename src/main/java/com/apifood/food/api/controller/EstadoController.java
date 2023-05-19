package com.apifood.food.api.controller;

import com.apifood.food.domain.exception.EntidadeEmUsoException;
import com.apifood.food.domain.exception.EntidadeNaoEncontradaException;
import com.apifood.food.domain.model.Estado;
import com.apifood.food.domain.model.Restaurante;
import com.apifood.food.domain.repository.EstadoRepository;
import com.apifood.food.domain.service.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private EstadoService estadoService;

    @GetMapping
    //(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}), especificando o tipo de objeto que retornata
    public List<Estado> listar(){
        return estadoRepository.listaEstado();
    }

    //@ResponseStatus(HttpStatus.OK)
    //A anotação @ResponseStatus é usada no Spring Boot para definir o código de status HTTP que deve ser retornado
    // em caso de sucesso ao manipular uma determinada exceção.
    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(@PathVariable("estadoId") Long id){
        //O ResponseEntity é uma classe do Spring Framework que representa toda a resposta HTTP, incluindo cabeçalhos, corpo
        // e status. Em outras palavras, o ResponseEntity permite que você personalize a resposta HTTP retornada ao cliente,
        // incluindo o código de status HTTP, cabeçalhos e corpo da resposta.
        //O ResponseEntity é frequentemente usado para retornar uma resposta HTTP personalizada de um controlador em um
        // aplicativo Spring Boot. Ao retornar um ResponseEntity em um controlador, você pode personalizar o código de status
        // HTTP, cabeçalhos e corpo da resposta.
        Estado estado = estadoRepository.buscarPeloId(id);

        //**return ResponseEntity.status(HttpStatus.OK).body(cozinha);
        //Em um contexto de requisição HTTP, o termo "body" refere-se ao conteúdo que é enviado na parte inferior da mensagem
        // de solicitação ou resposta HTTP, após os cabeçalhos.
        //O corpo da mensagem pode conter informações adicionais que são enviadas junto com a solicitação ou resposta.
        // Por exemplo, ao enviar uma solicitação POST para um servidor, o corpo da solicitação pode conter os dados que
        // estão sendo enviados para o servidor. Da mesma forma, ao receber uma resposta HTTP, o corpo da resposta pode conter
        // informações adicionais, como dados formatados em JSON ou XML.
        if (estado != null){
            return ResponseEntity.ok(estado);
        }
        //ResponseEntity.ok() é um método estático do ResponseEntity que retorna um objeto ResponseEntity com um código de
        // status HTTP 200 (OK) e nenhum corpo. Esse método é usado para indicar que uma solicitação HTTP foi bem-sucedida
        // e não há erros ou problemas a serem relatados.

        return ResponseEntity.notFound().build();
        //Caso nao tenha aquele id ele retonara esse status
    }

    @PostMapping
    public ResponseEntity<Estado> adicionar(@RequestBody Estado estado){
        /** Quando um cliente envia uma solicitação HTTP, ele pode enviar informações adicionais no corpo da solicitação,
         * além dos parâmetros da URL e dos cabeçalhos. Por exemplo, em uma solicitação POST ou PUT, os dados da solicitação
         * são frequentemente incluídos no corpo da solicitação. A anotação @RequestBody informa ao Spring Boot que ele deve
         * vincular o corpo da solicitação ao parâmetro do método anotado.
         * **/
        try {
            Estado estadoCadastrado = estadoService.salvar(estado);

            return ResponseEntity.status(HttpStatus.CREATED).body(estadoCadastrado);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().build();
            /**
             O método ResponseEntity.badRequest().build() é usado para criar uma resposta HTTP de status 400 Bad Request
             (Solicitação Inválida) em um serviço web. Quando ocorre um erro de solicitação inválida, como dados ausentes
             ou incorretos, você pode retornar essa resposta para indicar ao cliente que a solicitação não pôde ser processada
             devido a um problema com os dados fornecidos.**/
        }
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<?> atualizar(@PathVariable Long estadoId,
                                       @RequestBody Estado estado){
        try {

            Estado estadoAtualizado = estadoService.atualizar(estado, estadoId);
            return ResponseEntity.ok(estadoAtualizado);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<Estado> remover(@PathVariable Long estadoId){
        try {
            estadoService.excluir(estadoId);
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
