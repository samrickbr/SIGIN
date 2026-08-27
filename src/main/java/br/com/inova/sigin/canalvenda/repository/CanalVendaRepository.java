package br.com.inova.sigin.canalvenda.repository;

import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CanalVendaRepository extends JpaRepository<CanalVenda, Long> {

    List<CanalVenda> findByAtivoTrue();

    List<CanalVenda> findByAtivoFalse();

}