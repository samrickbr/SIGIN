package br.com.inova.sigin.local.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalRequest {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

}