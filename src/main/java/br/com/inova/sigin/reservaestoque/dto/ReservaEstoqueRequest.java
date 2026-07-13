package br.com.inova.sigin.reservaestoque.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaEstoqueRequest {

    @NotNull
    private Long materialId;

    @NotNull
    private Long ordemProducaoId;

    @NotNull
    @DecimalMin(value = "0.001")
    private BigDecimal quantidade;
}