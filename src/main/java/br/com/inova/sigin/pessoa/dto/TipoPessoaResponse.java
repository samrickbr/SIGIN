package br.com.inova.sigin.pessoa.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TipoPessoaResponse {

    private Long id;

    private String nome;

    private String descricao;

    private Boolean ativo;

    private LocalDateTime dataCriacao;
}