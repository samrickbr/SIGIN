package br.com.inova.sigin.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {

    @NotNull
    private Long pessoaId;

    @NotBlank
    private String login;

    @NotBlank
    private String senha;

    private Boolean ativo = true;
}