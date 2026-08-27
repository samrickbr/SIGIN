package br.com.inova.sigin.canalvenda.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CanalVendaResponse {

    private Long id;

    private String codigo;

    private String nome;

    private String descricao;

    private Boolean ativo;

}