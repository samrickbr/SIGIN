package br.com.inova.sigin.api.delivery.balcao.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BalcaoPedidoResponse {

    private Long id;

    private String numero;

    private String cliente;

    private String status;

    private BigDecimal valorTotal;

    private LocalDateTime dataPedido;

    private List<BalcaoItemResponse> itens;

}