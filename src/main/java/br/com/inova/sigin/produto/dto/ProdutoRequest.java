package br.com.inova.sigin.produto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRequest {

    @NotBlank
    private String nome;

    private String descricao;

    private Long categoriaId;

}