package br.com.inova.sigin.local.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LocalResponse(

        Long id,
        String nome,
        Boolean ativo,
        LocalDateTime dataCriacao

) {
}