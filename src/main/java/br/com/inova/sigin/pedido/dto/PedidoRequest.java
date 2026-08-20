package br.com.inova.sigin.pedido.dto;

import br.com.inova.sigin.pedido.enums.TipoRecebimento;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PedidoRequest {

    @NotNull
    private Long clienteId;

    private Long enderecoId;

    private TipoRecebimento tipoRecebimento;

    @NotNull
    private Long canalVendaId;

    private Long formaPagamentoId;

    private BigDecimal valorTotal;

    private BigDecimal taxaEntrega;

    private String observacao;

    private List<PedidoItemRequest> itens;

}