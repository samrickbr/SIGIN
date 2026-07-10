package br.com.inova.sigin.pessoa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PessoaRequest {

    @NotBlank
    private String nome;

    private String tipoDocumento;

    private String documento;

    private String telefone;

    private String email;

    private String observacao;
}