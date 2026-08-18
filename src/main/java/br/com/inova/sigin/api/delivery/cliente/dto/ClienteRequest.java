package br.com.inova.sigin.api.delivery.cliente.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteRequest {

    @NotBlank
    private String nome;

    @NotBlank
    private String telefone;

    @NotBlank
    private String documento;

    @NotBlank
    private String senha;

    private String email;
}