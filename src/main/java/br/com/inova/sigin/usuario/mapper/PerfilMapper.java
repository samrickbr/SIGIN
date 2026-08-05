package br.com.inova.sigin.usuario.mapper;

import br.com.inova.sigin.usuario.dto.PerfilResponse;
import br.com.inova.sigin.usuario.entity.Perfil;
import org.springframework.stereotype.Component;

@Component
public class PerfilMapper {

    public PerfilResponse toResponse(Perfil perfil) {

        if (perfil == null) {
            return null;
        }

        return PerfilResponse.builder()
                .id(perfil.getId())
                .nome(perfil.getNome())
                .descricao(perfil.getDescricao())
                .ativo(perfil.getAtivo())
                .build();
    }
}