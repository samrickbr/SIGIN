package br.com.inova.sigin.reservaestoque.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaEstoqueResponse {

    private Long id;

    private Long materialId;

    private String material;

    private Long ordemProducaoId;

    private String ordemProducao;

    private BigDecimal quantidade;

    private LocalDateTime dataReserva;

    private Boolean ativo;
}