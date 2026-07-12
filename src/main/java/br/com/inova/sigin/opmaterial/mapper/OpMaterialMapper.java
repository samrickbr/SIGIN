package br.com.inova.sigin.opmaterial.mapper;

import br.com.inova.sigin.opmaterial.dto.OpMaterialResponse;
import br.com.inova.sigin.opmaterial.entity.OpMaterial;
import org.springframework.stereotype.Component;

@Component
public class OpMaterialMapper {

    public OpMaterialResponse toResponse(OpMaterial entity) {

        return OpMaterialResponse.builder()
                .id(entity.getId())
                .ordemProducaoId(entity.getOrdemProducao().getId())
                .ordemProducao(entity.getOrdemProducao().getNumero())
                .materialId(entity.getMaterial().getId())
                .material(entity.getMaterial().getNome())
                .quantidadePlanejada(entity.getQuantidadePlanejada())
                .quantidadeConsumida(entity.getQuantidadeConsumida())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .build();
    }
}