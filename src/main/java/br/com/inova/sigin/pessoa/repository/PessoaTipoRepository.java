package br.com.inova.sigin.pessoa.repository;

import br.com.inova.sigin.pessoa.entity.PessoaTipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaTipoRepository extends JpaRepository<PessoaTipo, Long> {
    boolean existsByPessoaIdAndTipoPessoaId(Long pessoaId, Long tipoPessoaId);

}