package br.com.inova.sigin.produto.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProdutoResponse {

    private Long id;
    private String codigo;
    private String nome;
    private String descricao;
    private String categoria;
    private Boolean ativo;

}