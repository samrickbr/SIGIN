package br.com.inova.sigin.usuario.dto;

public record PermissaoResponse(

        Long id,

        String codigo,

        String descricao,

        Boolean ativo

) {
}