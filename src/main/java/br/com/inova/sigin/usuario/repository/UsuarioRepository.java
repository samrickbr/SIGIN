package br.com.inova.sigin.usuario.repository;

import br.com.inova.sigin.usuario.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLoginAndAtivoTrue(String login);

    Optional<Usuario> findByPessoaId(Long pessoaId);

    Optional<Usuario> findByIdAndAtivoTrue(Long id);

    List<Usuario> findByAtivoTrue();

    boolean existsByLogin(String login);

    boolean existsByPessoaId(Long pessoaId);
}