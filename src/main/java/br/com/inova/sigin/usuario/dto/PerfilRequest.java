package br.com.inova.sigin.usuario.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilRequest {

    @NotBlank
    private String nome;

    private String descricao;

    private Boolean ativo = true;
}