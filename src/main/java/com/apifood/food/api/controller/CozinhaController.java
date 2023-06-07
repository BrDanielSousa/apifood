package com.apifood.food.api.controller;

import com.apifood.food.domain.exception.EntidadeEmUsoException;
import com.apifood.food.domain.exception.EntidadeNaoEncontradaException;
import com.apifood.food.domain.model.Cozinha;
import com.apifood.food.domain.repository.CozinhaRepository;
import com.apifood.food.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@RestController
// A anotação @RestController em uma aplicação Spring Boot é usada para criar um controlador (controller) que lida com
// solicitações HTTP e retorna objetos serializados em um formato específico (como JSON, XML ou HTML) no corpo da resposta HTTP.
// Em outras palavras, a anotação @RestController é uma combinação das anotações @Controller e @ResponseBody.
// Ela indica que os métodos em um controlador anotado com @RestController devem retornar diretamente um objeto serializado no
// corpo da resposta HTTP.
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CozinhaService cozinhaService;

    @GetMapping //(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}), especificando o tipo de objeto que retornata
    public List<Cozinha> listar(){
        return cozinhaRepository.findAll();
    }

    //@ResponseStatus(HttpStatus.OK)
    //A anotação @ResponseStatus é usada no Spring Boot para definir o código de status HTTP que deve ser retornado
    // em caso de sucesso ao manipular uma determinada exceção.
    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id){
        //O ResponseEntity é uma classe do Spring Framework que representa toda a resposta HTTP, incluindo cabeçalhos, corpo
        // e status. Em outras palavras, o ResponseEntity permite que você personalize a resposta HTTP retornada ao cliente,
        // incluindo o código de status HTTP, cabeçalhos e corpo da resposta.
        //O ResponseEntity é frequentemente usado para retornar uma resposta HTTP personalizada de um controlador em um
        // aplicativo Spring Boot. Ao retornar um ResponseEntity em um controlador, você pode personalizar o código de status
        // HTTP, cabeçalhos e corpo da resposta.
        Optional<Cozinha> cozinha = cozinhaRepository.findById(id);

//      Ao chamar o método .isPresent() no objeto cozinha, ele retorna true se o valor estiver presente e false caso contrário.
        if (cozinha.isPresent()){

            return ResponseEntity.ok(cozinha.get());
            //ResponseEntity.ok() é um método estático do ResponseEntity que retorna um objeto ResponseEntity com um código de
            // status HTTP 200 (OK) e nenhum corpo. Esse método é usado para indicar que uma solicitação HTTP foi bem-sucedida
            // e não há erros ou problemas a serem relatados.
        }

        return ResponseEntity.notFound().build();
        //Caso nao tenha aquele id ele retonara esse status
    }

    /*@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionar(@RequestBody Cozinha cozinha){
        *//** Quando um cliente envia uma solicitação HTTP, ele pode enviar informações adicionais no corpo da solicitação,
         * além dos parâmetros da URL e dos cabeçalhos. Por exemplo, em uma solicitação POST ou PUT, os dados da solicitação
         * são frequentemente incluídos no corpo da solicitação. A anotação @RequestBody informa ao Spring Boot que ele deve
         * vincular o corpo da solicitação ao parâmetro do método anotado.
         * **//*
        cozinhaRepository.adicionar(cozinha);
    }*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public Cozinha adicionar(@RequestBody Cozinha cozinha){
        /** Quando um cliente envia uma solicitação HTTP, ele pode enviar informações adicionais no corpo da solicitação,
         * além dos parâmetros da URL e dos cabeçalhos. Por exemplo, em uma solicitação POST ou PUT, os dados da solicitação
         * são frequentemente incluídos no corpo da solicitação. A anotação @RequestBody informa ao Spring Boot que ele deve
         * vincular o corpo da solicitação ao parâmetro do método anotado.
         * **/
        return cozinhaService.salvar(cozinha);
    }

    @Transactional
    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId,
                                             @RequestBody Cozinha cozinha){

        Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);

        if(cozinhaAtual.isPresent()){
            //cozinhaAtual.setNome(cozinha.getNome());
            BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
            /**
             * BeanUtils é uma classe da biblioteca Apache Commons BeanUtils que fornece um conjunto de métodos estáticos
             * para manipulação de propriedades de objetos Java por reflexão.
             * Algumas das funcionalidades fornecidas pela classe BeanUtils são:
             * Copiar os valores das propriedades de um objeto para outro objeto;
             * Popular um objeto Java com os valores enviados em uma requisição HTTP;
             * Obter o valor de uma propriedade de um objeto Java;
             * Definir o valor de uma propriedade de um objeto Java;
             * Converter o valor de uma propriedade de um tipo para outro tipo.
             * A classe BeanUtils fornece métodos para lidar com diferentes tipos de objetos Java, incluindo Map, List e Array.**/

            /**BeanUtils.copyProperties() é um método da biblioteca Apache Commons BeanUtils que permite copiar os valores das
             * propriedades de um objeto de origem para um objeto de destino. O método faz uma cópia dos valores de todas as
             * propriedades correspondentes da origem para o destino.**/

            Cozinha cozinhaSalva = cozinhaService.salvar(cozinhaAtual.get());
            return ResponseEntity.ok(cozinhaSalva);
        }

        return ResponseEntity.notFound().build();
        /**A resposta HTTP criada será retornada pelo controlador do Spring para indicar que o recurso solicitado não foi encontrado.
         * Isso é comumente usado em cenários em que uma solicitação é feita para um recurso inexistente, e o servidor precisa informar
         * ao cliente que o recurso não está disponível.**/
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId){
        try {
            cozinhaService.excluir(cozinhaId);
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
