package br.com.inova.sigin.pessoa.repository;

import br.com.inova.sigin.pessoa.entity.PessoaEndereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PessoaEnderecoRepository
        extends JpaRepository<PessoaEndereco, Long> {

    List<PessoaEndereco> findByPessoaIdOrderByPrincipalDescIdAsc(Long pessoaId);

    Optional<PessoaEndereco> findByIdAndPessoaId(
            Long id,
            Long pessoaId
    );

    boolean existsByPessoaIdAndPrincipalTrue(Long pessoaId);
}