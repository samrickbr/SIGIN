package br.com.inova.sigin.produto.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CategoriaResponse {

    private Long id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
}