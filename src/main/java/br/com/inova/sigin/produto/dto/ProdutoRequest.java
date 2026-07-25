package br.com.inova.sigin.produto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoRequest {

    @NotBlank
    private String nome;

    private String descricao;

    private Long categoriaId;

    private BigDecimal precoVenda;

    private Boolean disponivelVenda;

    private String imagem;

}