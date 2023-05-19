package com.apifood.food.api.controller;

import com.apifood.food.domain.exception.EntidadeEmUsoException;
import com.apifood.food.domain.exception.EntidadeNaoEncontradaException;
import com.apifood.food.domain.model.Restaurante;
import com.apifood.food.domain.repository.RestauranteRepository;
import com.apifood.food.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.listaRestaurante();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id){
        Restaurante restaurante = restauranteRepository.buscarPeloId(id);

        if (restaurante != null){
            return ResponseEntity.ok(restaurante);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Restaurante> adicionar(@RequestBody Restaurante restaurante){
        /** Quando um cliente envia uma solicitação HTTP, ele pode enviar informações adicionais no corpo da solicitação,
         * além dos parâmetros da URL e dos cabeçalhos. Por exemplo, em uma solicitação POST ou PUT, os dados da solicitação
         * são frequentemente incluídos no corpo da solicitação. A anotação @RequestBody informa ao Spring Boot que ele deve
         * vincular o corpo da solicitação ao parâmetro do método anotado.
         * **/
        try {
            Restaurante restauranteCadastrado = restauranteService.salvar(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED).body(restauranteCadastrado);
        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().build();
            /**
             O método ResponseEntity.badRequest().build() é usado para criar uma resposta HTTP de status 400 Bad Request
             (Solicitação Inválida) em um serviço web. Quando ocorre um erro de solicitação inválida, como dados ausentes
             ou incorretos, você pode retornar essa resposta para indicar ao cliente que a solicitação não pôde ser processada
             devido a um problema com os dados fornecidos.**/
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
                                             @RequestBody Restaurante restaurante){
        try {
            Restaurante restauranteAtual = restauranteRepository.buscarPeloId(restauranteId);

            if (restauranteAtual == null){
                return ResponseEntity.notFound().build();
            }

            Restaurante restauranteAtualizado = restauranteService.atualizar(restauranteAtual, restaurante);
            return ResponseEntity.ok(restauranteAtualizado);

        }catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PatchMapping("/{restauranteId}")
//    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
//                                              @RequestBody Map<String, Objects> campos){
//       Restaurante restauranteAtual = restauranteRepository.buscarPeloId(restauranteId);
//
//        if (restauranteAtual == null){
//            return ResponseEntity.notFound().build();
//        }
//
//        merge(campos, restauranteAtual);
//
//       return ResponseEntity.ok().build();
//    }
//
//    public void merge(Map<String, Objects> dadosOrigem, Restaurante restauranteDestino){
//        ObjectMapper objectMapper = new ObjectMapper();
//        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//        dadosOrigem.forEach((nomePropiedade, valorPropiedade)->{
//            Field field = ReflectionUtils.findField(Restaurante.class, nomePropiedade);
//            field.setAccessible(true);
//
//            Objects novoValor = (Objects) ReflectionUtils.getField(field, restauranteOrigem);
//
//            ReflectionUtils.setField(field, restauranteOrigem, novoValor);
//        });
//    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId){
        try {
            restauranteService.excluir(restauranteId);
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
