package com.apifood.food.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

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
public class Estado {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
}
