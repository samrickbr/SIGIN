package br.com.inova.sigin.local.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalUpdateRequest {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    private Boolean ativo;

}