package br.com.inova.sigin.api.delivery.balcao.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class BalcaoItemResponse {

    private Long id;

    private String produto;

    private BigDecimal quantidade;

}