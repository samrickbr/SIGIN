package br.com.inova.sigin.usuario.mapper;

import br.com.inova.sigin.usuario.dto.PermissaoResponse;
import br.com.inova.sigin.usuario.entity.Permissao;
import org.springframework.stereotype.Component;

@Component
public class PermissaoMapper {

    public PermissaoResponse toResponse(Permissao permissao) {

        return new PermissaoResponse(
                permissao.getId(),
                permissao.getCodigo(),
                permissao.getDescricao(),
                permissao.getAtivo()
        );
    }
}