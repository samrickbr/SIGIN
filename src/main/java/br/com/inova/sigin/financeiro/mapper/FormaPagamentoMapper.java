package br.com.inova.sigin.financeiro.mapper;

import br.com.inova.sigin.financeiro.dto.FormaPagamentoResponse;
import br.com.inova.sigin.financeiro.entity.FormaPagamento;

public class FormaPagamentoMapper {

    private FormaPagamentoMapper() {}

    public static FormaPagamentoResponse toDTO(FormaPagamento entity) {

        return new FormaPagamentoResponse(
                entity.getId(),
                entity.getDescricao(),
                entity.getAtivo(),
                entity.getBaixaAutomatica()
        );
    }
}