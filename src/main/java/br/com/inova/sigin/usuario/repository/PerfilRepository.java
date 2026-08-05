package br.com.inova.sigin.usuario.repository;

import br.com.inova.sigin.usuario.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    List<Perfil> findByAtivoTrue();

    Optional<Perfil> findByIdAndAtivoTrue(Long id);

    boolean existsByNome(String nome);
}