package br.com.inova.sigin.pessoa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TipoPessoaRequest {

    @NotBlank
    private String nome;

    private String descricao;
}