package br.com.inova.sigin.api.delivery.carrinho.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CriarCarrinhoRequest {

    @NotNull
    private Long clienteId;

}