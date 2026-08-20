package br.com.inova.sigin.pessoa.dto;

import br.com.inova.sigin.pessoa.entity.PessoaEndereco;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PessoaEnderecoResponse {

    private Long id;

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String uf;

    private Boolean principal;

    public static PessoaEnderecoResponse from(PessoaEndereco endereco) {
        return PessoaEnderecoResponse.builder()
                .id(endereco.getId())
                .cep(endereco.getCep())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .uf(endereco.getUf())
                .principal(endereco.getPrincipal())
                .build();
    }
}