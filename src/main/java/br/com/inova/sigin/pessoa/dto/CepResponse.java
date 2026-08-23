package br.com.inova.sigin.pessoa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CepResponse {

    private String cep;

    private String logradouro;

    private String complemento;

    private String bairro;

    @JsonProperty("localidade")
    private String cidade;

    private String uf;

    private String ibge;

    private String gia;

    private String ddd;

    private String siafi;

    private Boolean erro;
}