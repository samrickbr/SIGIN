package br.com.inova.sigin.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record PermissaoRequest(

        @NotBlank
        String codigo,

        String descricao,

        Boolean ativo

) {
}