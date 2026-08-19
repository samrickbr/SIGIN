package br.com.inova.sigin.pessoa.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PessoaEnderecoResponse {

    private Long id;

    private Long pessoaId;

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String uf;

    private Boolean principal;
}