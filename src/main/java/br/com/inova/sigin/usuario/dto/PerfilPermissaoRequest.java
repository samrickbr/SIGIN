package br.com.inova.sigin.usuario.dto;

import jakarta.validation.constraints.NotNull;

public record PerfilPermissaoRequest(

        @NotNull
        Long perfilId,

        @NotNull
        Long permissaoId

) {
}