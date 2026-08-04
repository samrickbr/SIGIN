package br.com.inova.sigin.financeiro.dto;

public record FormaPagamentoResponse(

        Long id,
        String descricao,
        Boolean ativo,
        Boolean baixaAutomatica

) {}