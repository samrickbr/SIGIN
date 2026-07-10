package br.com.inova.sigin.pessoa.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PessoaTipoRequest {

    @NotNull
    private Long tipoPessoaId;
}