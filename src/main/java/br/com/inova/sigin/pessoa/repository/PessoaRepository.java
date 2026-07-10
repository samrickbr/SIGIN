package br.com.inova.sigin.pessoa.repository;

import br.com.inova.sigin.pessoa.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

}