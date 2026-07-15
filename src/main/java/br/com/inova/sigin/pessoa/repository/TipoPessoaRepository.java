package br.com.inova.sigin.pessoa.repository;

import br.com.inova.sigin.pessoa.entity.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoPessoaRepository extends JpaRepository<TipoPessoa, Long> {

    boolean existsByNomeIgnoreCase(String nome);
    Optional<TipoPessoa> findByNomeIgnoreCase(String nome);
}