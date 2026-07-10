package br.com.inova.sigin.local.repository;

import br.com.inova.sigin.local.entity.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocalRepository extends JpaRepository<Local, Long> {

    Optional<Local> findByNomeIgnoreCase(String nome);

    List<Local> findByAtivoTrue();

    List<Local> findByAtivoFalse();

    boolean existsByNomeIgnoreCase(String nome);
}