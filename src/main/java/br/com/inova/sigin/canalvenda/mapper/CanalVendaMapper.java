package br.com.inova.sigin.canalvenda.mapper;

import br.com.inova.sigin.canalvenda.dto.CanalVendaResponse;
import br.com.inova.sigin.canalvenda.entity.CanalVenda;

public final class CanalVendaMapper {

    private CanalVendaMapper() {
    }

    public static CanalVendaResponse toResponse(CanalVenda entity) {
        return CanalVendaResponse.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .ativo(entity.getAtivo())
                .build();
    }
}