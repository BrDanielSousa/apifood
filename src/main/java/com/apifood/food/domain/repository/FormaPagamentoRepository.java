package com.apifood.food.domain.repository;

import com.apifood.food.domain.model.FormaPagamento;

import java.util.List;

public interface FormaPagamentoRepository {

    List<FormaPagamento> listaFormaPagamento();
    FormaPagamento buscarPeloId(Long id);
    FormaPagamento adicionar(FormaPagamento formaPagamento);
    void remover(Long id);
}
