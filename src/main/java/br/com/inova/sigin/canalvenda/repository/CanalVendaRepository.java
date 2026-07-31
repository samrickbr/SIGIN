package br.com.inova.sigin.canalvenda.repository;

import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CanalVendaRepository extends JpaRepository<CanalVenda, Long> {

    Optional<CanalVenda> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);

}