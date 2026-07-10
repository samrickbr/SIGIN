package br.com.inova.sigin.pessoa.dto;

import lombok.Data;

@Data
public class PessoaUpdateRequest {

    private String nome;

    private String tipoDocumento;

    private String documento;

    private String telefone;

    private String email;

    private String observacao;

    private Boolean ativo;
}