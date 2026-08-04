package br.com.inova.sigin.financeiro.dto;

import br.com.inova.sigin.financeiro.enums.OrigemMovimentoCaixa;
import br.com.inova.sigin.financeiro.enums.TipoMovimentoCaixa;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CaixaMovimentoResponse(

        Long id,

        TipoMovimentoCaixa tipo,

        BigDecimal valor,

        LocalDateTime dataMovimento,

        OrigemMovimentoCaixa origem,

        Long referenciaId,

        String observacao

) {}