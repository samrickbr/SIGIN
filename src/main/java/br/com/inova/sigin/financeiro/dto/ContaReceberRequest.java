package br.com.inova.sigin.financeiro.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContaReceberRequest(

        @NotNull
        Long pedidoId,

        @NotNull
        Long pessoaId,

        @NotNull
        Long formaPagamentoId,

        @NotNull
        BigDecimal valor,

        @NotNull
        LocalDate dataVencimento

) {}