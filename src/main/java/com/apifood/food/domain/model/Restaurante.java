package com.apifood.food.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
// a anotação @Data é usada para gerar automaticamente os
// métodos de acesso (getName(), setName(), getAge(), setAge()) e os
// métodos toString(), equals() e hashCode().
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//Ao usar a anotação @EqualsAndHashCode, é possível personalizar quais campos da classe serão usados
// na comparação de igualdade e no cálculo do código hash. O parâmetro onlyExplicitlyIncluded =
// true indica que apenas os campos marcados explicitamente com a anotação @EqualsAndHashCode.Include
// devem ser considerados na geração desses métodos.
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include //Campo Id Marcado Com a Anotcao para equals e hashcode
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_frete", nullable = false)//Renomear o nome do campo
    private BigDecimal taxaFrete;

    @ManyToOne
    @JoinColumn(nullable = false) //renomear o nome do campo da relação
    private Cozinha cozinha;

    @JsonIgnore
    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "timestamp")
    private LocalDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "timestamp")//Nao posso usar o datatime pq e do mysql
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
                joinColumns = @JoinColumn(name = "restaurante_id"),
                inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formaPagamentos = new ArrayList<>();

}
