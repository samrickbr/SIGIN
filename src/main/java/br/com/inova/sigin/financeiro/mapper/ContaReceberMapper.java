package br.com.inova.sigin.financeiro.mapper;

import br.com.inova.sigin.financeiro.dto.ContaReceberResponse;
import br.com.inova.sigin.financeiro.entity.ContaReceber;

public class ContaReceberMapper {

    private ContaReceberMapper() {}

    public static ContaReceberResponse toResponse(
            ContaReceber conta
    ){
        return new ContaReceberResponse(
                conta.getId(),
                conta.getPedido().getId(),
                conta.getPessoa().getId(),
                conta.getValor(),
                conta.getDataVencimento(),
                conta.getStatus()
        );
    }
}