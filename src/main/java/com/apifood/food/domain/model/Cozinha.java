package com.apifood.food.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
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
public class Cozinha {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@JsonProperty("titulo") Quando você usa a anotação @JsonProperty em uma propriedade de uma classe, você pode
    // especificar o nome da propriedade JSON correspondente. Por exemplo, se você tem uma classe Person com uma
    // propriedade firstName, mas deseja que ela seja serializada como first_name em JSON, você pode usar a anotação
    // @JsonProperty("first_name") na propriedade firstName.
    @Column(nullable = false)
    private String nome;

    @JsonIgnore
    @OneToMany(mappedBy = "cozinha")
    private List<Restaurante> restaurantes = new ArrayList();
}
