package br.com.inova.sigin.usuario.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PerfilResponse {

    private Long id;

    private String nome;

    private String descricao;

    private Boolean ativo;
}