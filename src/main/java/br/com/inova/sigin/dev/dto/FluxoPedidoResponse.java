package br.com.inova.sigin.dev.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class FluxoPedidoResponse {

    private Long pedidoId;

    private List<Long> ordensProducao;

    private String status;

}