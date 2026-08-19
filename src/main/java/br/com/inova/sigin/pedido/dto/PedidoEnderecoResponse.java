package br.com.inova.sigin.pedido.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PedidoEnderecoResponse {

    private Long id;

    private Long pessoaEnderecoId;

    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String uf;
}