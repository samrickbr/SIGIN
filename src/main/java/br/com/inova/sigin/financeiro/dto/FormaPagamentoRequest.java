package br.com.inova.sigin.financeiro.dto;

import jakarta.validation.constraints.NotBlank;

public record FormaPagamentoRequest(

        @NotBlank
        String descricao,

        Boolean baixaAutomatica

) {}