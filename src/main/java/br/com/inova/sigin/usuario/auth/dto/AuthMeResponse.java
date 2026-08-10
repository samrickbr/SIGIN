package br.com.inova.sigin.usuario.auth.dto;

import br.com.inova.sigin.pessoa.dto.PessoaResponse;
import br.com.inova.sigin.usuario.dto.PerfilResponse;
import br.com.inova.sigin.usuario.dto.PermissaoResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AuthMeResponse {

    private Long id;

    private String login;

    private Boolean ativo;

    private PessoaResponse pessoa;

    private List<PerfilResponse> perfis;

    private List<PermissaoResponse> permissoes;
}