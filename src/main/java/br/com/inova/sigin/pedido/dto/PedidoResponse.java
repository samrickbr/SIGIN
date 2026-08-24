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

    private BigDecimal valorProdutos;

    private BigDecimal taxaEntrega;

    private BigDecimal valorTotal;

    private String status;

    private List<PedidoPagamentoResponse> pagamentos;

    private List<PedidoItemResponse> itens;

    private Boolean ativo;

    private String observacao;
}