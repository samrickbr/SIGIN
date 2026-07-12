package br.com.inova.sigin.produtomaterial.mapper;

import br.com.inova.sigin.produtomaterial.dto.ProdutoMaterialResponse;
import br.com.inova.sigin.produtomaterial.entity.ProdutoMaterial;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMaterialMapper {

    public ProdutoMaterialResponse toResponse(ProdutoMaterial entity) {

        return ProdutoMaterialResponse.builder()
                .id(entity.getId())
                .produtoId(entity.getProduto().getId())
                .produto(entity.getProduto().getNome())
                .materialId(entity.getMaterial().getId())
                .material(entity.getMaterial().getNome())
                .quantidade(entity.getQuantidade())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .build();
    }
}