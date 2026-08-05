package br.com.inova.sigin.usuario.repository;

import br.com.inova.sigin.usuario.entity.PerfilPermissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerfilPermissaoRepository
        extends JpaRepository<PerfilPermissao, Long> {


    Optional<PerfilPermissao> findByPerfilIdAndPermissaoId(
            Long perfilId,
            Long permissaoId
    );

}