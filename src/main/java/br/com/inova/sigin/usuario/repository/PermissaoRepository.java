package br.com.inova.sigin.usuario.repository;

import br.com.inova.sigin.usuario.entity.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    Optional<Permissao> findByCodigoIgnoreCase(String codigo);

}