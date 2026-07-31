package br.com.inova.sigin.canalvenda.mapper;

import br.com.inova.sigin.canalvenda.dto.CanalVendaRequest;
import br.com.inova.sigin.canalvenda.dto.CanalVendaResponse;
import br.com.inova.sigin.canalvenda.entity.CanalVenda;

public class CanalVendaMapper {

    private CanalVendaMapper() {
    }

    public static CanalVenda toEntity(CanalVendaRequest request) {
        return CanalVenda.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .ativo(request.getAtivo())
                .build();
    }

    public static CanalVendaResponse toResponse(CanalVenda entity) {
        return CanalVendaResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .ativo(entity.getAtivo())
                .build();
    }

    public static void updateEntity(CanalVenda entity, CanalVendaRequest request) {
        entity.setNome(request.getNome());
        entity.setDescricao(request.getDescricao());
        entity.setAtivo(request.getAtivo());
    }

}