package br.com.inova.sigin.canalvenda.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CanalVendaRequest {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(max = 80)
    private String nome;

    @Size(max = 255)
    private String descricao;

    private Boolean ativo = true;

}