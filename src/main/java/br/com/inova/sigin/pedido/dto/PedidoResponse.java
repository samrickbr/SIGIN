package br.com.inova.sigin.pedido.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PedidoResponse {

    private Long id;

    private String numero;

    private Long clienteId;

    private String cliente;

    private PedidoEnderecoResponse endereco;

    private Long canalVendaId;

    private String canalVenda;

    private LocalDateTime dataPedido;

    private BigDecimal valorTotal;

    private String status;

    private Long formaPagamentoId;

    private String formaPagamento;

    private Boolean ativo;

    private String observacao;
}