package br.com.inova.sigin.api.delivery.cliente.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteOperacionalResponse {

    private Long id;
    private String nome;
    private String documento;
    private String telefone;
    private String email;

}
