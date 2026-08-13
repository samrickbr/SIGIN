package br.com.inova.sigin.pessoa.repository;

import br.com.inova.sigin.pessoa.entity.PessoaTipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaTipoRepository extends JpaRepository<PessoaTipo, Long> {
    boolean existsByPessoaIdAndTipoPessoaId(Long pessoaId, Long tipoPessoaId);
    Optional<PessoaTipo> findByPessoaIdAndTipoPessoaId(
            Long pessoaId,
            Long tipoPessoaId
    );

}