package br.com.inova.sigin.pessoa.repository;

import br.com.inova.sigin.pessoa.entity.Pessoa;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @EntityGraph(attributePaths = {"tipos", "tipos.tipoPessoa"})
    List<Pessoa> findAll();

    @EntityGraph(attributePaths = {"tipos", "tipos.tipoPessoa"})
    Optional<Pessoa> findById(Long id);

    boolean existsByNomeIgnoreCase(String nome);

    Optional<Pessoa> findByNomeIgnoreCase(String nome);

    Optional<Pessoa> findByTelefone(String telefone);

    Optional<Pessoa> findByDocumento(String documento);

    @Query("""
        SELECT DISTINCT p
        FROM Pessoa p
        JOIN p.tipos pt
        JOIN pt.tipoPessoa tp
        WHERE p.ativo = true
          AND tp.nome = 'CLIENTE'
          AND (
                LOWER(p.nome) LIKE LOWER(CONCAT('%', :busca, '%'))
                OR LOWER(COALESCE(p.documento, '')) LIKE LOWER(CONCAT('%', :busca, '%'))
                OR LOWER(COALESCE(p.telefone, '')) LIKE LOWER(CONCAT('%', :busca, '%'))
          )
        ORDER BY p.nome
        """)
    List<Pessoa> pesquisarClientes(
            @Param("busca") String busca
    );

}
