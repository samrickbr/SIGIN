package br.com.inova.sigin.financeiro.dto;

import br.com.inova.sigin.financeiro.enums.StatusContaReceber;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ContaReceberResponse(

        Long id,
        Long pedidoId,
        Long pessoaId,
        BigDecimal valor,
        LocalDate dataVencimento,
        StatusContaReceber status

) {}