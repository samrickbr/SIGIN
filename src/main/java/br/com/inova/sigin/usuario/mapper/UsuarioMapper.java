package br.com.inova.sigin.usuario.mapper;

import br.com.inova.sigin.usuario.dto.UsuarioResponse;
import br.com.inova.sigin.usuario.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponse toResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return UsuarioResponse.builder()
                .id(usuario.getId())
                .pessoaId(usuario.getPessoa().getId())
                .login(usuario.getLogin())
                .ativo(usuario.getAtivo())
                .ultimoLogin(usuario.getUltimoLogin())
                .dataCriacao(usuario.getDataCriacao())
                .build();
    }
}