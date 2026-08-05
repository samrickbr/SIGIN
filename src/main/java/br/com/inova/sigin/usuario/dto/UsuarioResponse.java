package br.com.inova.sigin.usuario.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UsuarioResponse {

    private Long id;

    private Long pessoaId;

    private String login;

    private Boolean ativo;

    private LocalDateTime ultimoLogin;

    private LocalDateTime dataCriacao;
}