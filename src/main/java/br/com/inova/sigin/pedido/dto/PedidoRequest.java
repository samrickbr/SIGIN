package br.com.inova.sigin.pedido.dto;

import br.com.inova.sigin.configuracao.service.ConfiguracaoSistemaService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoRequest {

    @NotNull
    private Long clienteId;

    private BigDecimal valorTotal;

    private String observacao;
}