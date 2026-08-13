package br.com.inova.sigin.produto.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaRequest {

    @NotBlank(message = "Nome da categoria é obrigatório")
    private String nome;

    private String descricao;

    private Boolean ativo;
}