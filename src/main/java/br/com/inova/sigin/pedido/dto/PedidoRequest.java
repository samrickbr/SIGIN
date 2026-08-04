package br.com.inova.sigin.pedido.dto;

import br.com.inova.sigin.configuracao.service.ConfiguracaoSistemaService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoRequest {

    @NotNull
    private Long clienteId;

    @NotNull
    private Long canalVendaId;

    private Long formaPagamentoId;

    private BigDecimal valorTotal;

    private String observacao;

    private List<PedidoItemRequest> itens;

}