package br.com.inova.sigin.usuario.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String token;

    @Builder.Default
    private String tipo = "Bearer";
}