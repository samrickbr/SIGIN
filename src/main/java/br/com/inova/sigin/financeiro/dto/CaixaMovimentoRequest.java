package br.com.inova.sigin.financeiro.dto;

import br.com.inova.sigin.financeiro.enums.OrigemMovimentoCaixa;
import br.com.inova.sigin.financeiro.enums.TipoMovimentoCaixa;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CaixaMovimentoRequest(

        @NotNull
        TipoMovimentoCaixa tipo,

        @NotNull
        BigDecimal valor,

        OrigemMovimentoCaixa origem,

        Long referenciaId,

        String observacao

) {}