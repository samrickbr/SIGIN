package br.com.inova.sigin.usuario.repository;

import br.com.inova.sigin.usuario.entity.UsuarioPerfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioPerfilRepository
        extends JpaRepository<UsuarioPerfil, Long> {

    boolean existsByUsuarioIdAndPerfilId(
            Long usuarioId,
            Long perfilId
    );

    @Query("""
        SELECT DISTINCT up
        FROM UsuarioPerfil up
        JOIN FETCH up.perfil p
        LEFT JOIN FETCH p.permissoes pp
        LEFT JOIN FETCH pp.permissao
        WHERE up.usuario.id = :usuarioId
    """)
    List<UsuarioPerfil> buscarComPermissoesPorUsuario(
            Long usuarioId
    );
}
