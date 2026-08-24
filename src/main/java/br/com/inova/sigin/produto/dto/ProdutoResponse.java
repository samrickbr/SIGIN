package br.com.inova.sigin.produto.dto;

import br.com.inova.sigin.produto.enums.Setor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class ProdutoResponse {

    private Long id;
    private String codigo;
    private String nome;
    private String descricao;
    private String categoria;
    private BigDecimal precoVenda;
    private Boolean disponivelVenda;
    private String imagem;
    private Boolean ativo;
    private Long categoriaId;
    private Setor setor;
}