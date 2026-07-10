package br.com.inova.sigin.pessoa.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PessoaResponse {

    private Long id;

    private String nome;

    private String tipoDocumento;

    private String documento;

    private String telefone;

    private String email;

    private String observacao;

    private Boolean ativo;

    private LocalDateTime dataCriacao;

    private List<String> tipos;
}