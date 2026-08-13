package br.com.inova.sigin.produto.mapper;

import br.com.inova.sigin.produto.dto.CategoriaRequest;
import br.com.inova.sigin.produto.dto.CategoriaResponse;
import br.com.inova.sigin.produto.entity.Categoria;

public final class CategoriaMapper {

    private CategoriaMapper() {
    }

    public static Categoria toEntity(CategoriaRequest request) {
        return Categoria.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .ativo(request.getAtivo())
                .build();
    }

    public static CategoriaResponse toResponse(Categoria entity) {
        return CategoriaResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .descricao(entity.getDescricao())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .build();
    }

    public static void updateEntity(
            Categoria entity,
            CategoriaRequest request
    ) {
        entity.setNome(request.getNome());
        entity.setDescricao(request.getDescricao());

        if (request.getAtivo() != null) {
            entity.setAtivo(request.getAtivo());
        }
    }
}