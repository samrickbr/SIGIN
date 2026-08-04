package br.com.inova.sigin.financeiro.mapper;

import br.com.inova.sigin.financeiro.dto.CaixaMovimentoResponse;
import br.com.inova.sigin.financeiro.entity.CaixaMovimento;

public class CaixaMovimentoMapper {


    private CaixaMovimentoMapper(){}


    public static CaixaMovimentoResponse toResponse(
            CaixaMovimento movimento
    ){

        return new CaixaMovimentoResponse(
                movimento.getId(),
                movimento.getTipo(),
                movimento.getValor(),
                movimento.getDataMovimento(),
                movimento.getOrigem(),
                movimento.getReferenciaId(),
                movimento.getObservacao()
        );
    }
}