package br.com.inova.sigin.usuario.mapper;

import br.com.inova.sigin.usuario.dto.PerfilPermissaoResponse;
import br.com.inova.sigin.usuario.entity.PerfilPermissao;
import org.springframework.stereotype.Component;

@Component
public class PerfilPermissaoMapper {

    public PerfilPermissaoResponse toResponse(
            PerfilPermissao entity
    ) {

        return new PerfilPermissaoResponse(
                entity.getId(),
                entity.getPerfil().getId(),
                //entity.getPerfil().getNome(),
                entity.getPermissao().getId()
                //entity.getPermissao().getCodigo()
        );
    }
}