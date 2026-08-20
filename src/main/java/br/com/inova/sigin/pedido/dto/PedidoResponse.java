package br.com.inova.sigin.pedido.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PedidoResponse {

    private Long id;

    private String numero;

    private Long clienteId;

    private String cliente;

    private PedidoEnderecoResponse endereco;

    private String tipoRecebimento;

    private Long canalVendaId;

    private String canalVenda;

    private LocalDateTime dataPedido;

    private BigDecimal valorTotal;

    private BigDecimal taxaEntrega;

    private String status;

    private List<PedidoPagamentoResponse> pagamentos;

    private Boolean ativo;

    private String observacao;
}