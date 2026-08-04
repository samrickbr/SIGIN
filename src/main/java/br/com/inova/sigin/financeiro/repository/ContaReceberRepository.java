package br.com.inova.sigin.financeiro.repository;

import br.com.inova.sigin.financeiro.entity.ContaReceber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaReceberRepository
        extends JpaRepository<ContaReceber, Long> {

}