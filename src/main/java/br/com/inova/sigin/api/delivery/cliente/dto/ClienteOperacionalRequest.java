package br.com.inova.sigin.api.delivery.cliente.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteOperacionalRequest {

    @NotBlank
    private String nome;

    private String documento;

    private String telefone;

    private String email;

}
