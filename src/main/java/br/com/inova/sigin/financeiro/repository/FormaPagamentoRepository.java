package br.com.inova.sigin.financeiro.repository;

import br.com.inova.sigin.financeiro.entity.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    List<FormaPagamento> findByAtivoTrue();
}