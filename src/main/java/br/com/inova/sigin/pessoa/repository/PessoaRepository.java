package br.com.inova.sigin.pessoa.repository;

import br.com.inova.sigin.pessoa.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    @EntityGraph(attributePaths = {"tipos", "tipos.tipoPessoa"})
    List<Pessoa> findAll();
    @EntityGraph(attributePaths = {"tipos", "tipos.tipoPessoa"})
    Optional<Pessoa> findById(Long id);

    boolean existsByNomeIgnoreCase(String nome);
    Optional<Pessoa> findByNomeIgnoreCase(String nome);

}