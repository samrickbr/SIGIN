package br.com.inova.sigin.material.mapper;

import br.com.inova.sigin.material.dto.MaterialRequest;
import br.com.inova.sigin.material.dto.MaterialResponse;
import br.com.inova.sigin.material.entity.Material;
import br.com.inova.sigin.shared.util.StringUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MaterialMapper {

    public Material toEntity(MaterialRequest request) {

        return Material.builder()
                .codigo(request.getCodigo().trim().toUpperCase())
                .nome(StringUtil.normalizarNome(request.getNome()))
                .descricao(request.getDescricao())
                .unidadeMedida(request.getUnidadeMedida().trim().toUpperCase())
                .estoqueMinimo(request.getEstoqueMinimo())
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();
    }

    public MaterialResponse toResponse(Material material) {

        return MaterialResponse.builder()
                .id(material.getId())
                .codigo(material.getCodigo())
                .nome(material.getNome())
                .descricao(material.getDescricao())
                .unidadeMedida(material.getUnidadeMedida())
                .estoqueMinimo(material.getEstoqueMinimo())
                .ativo(material.getAtivo())
                .dataCriacao(material.getDataCriacao())
                .build();
    }
}