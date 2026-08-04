package br.com.inova.sigin.financeiro.repository;

import br.com.inova.sigin.financeiro.entity.CaixaMovimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaMovimentoRepository
        extends JpaRepository<CaixaMovimento, Long> {
}