package br.com.inova.sigin.ordemproducao.repository;

import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdemProducaoRepository extends JpaRepository<OrdemProducao, Long> {

    boolean existsByNumero(String numero);

    @EntityGraph(attributePaths = {
            "produto",
            "localDestino",
            "responsavel"
    })
    List<OrdemProducao> findAll();

    @EntityGraph(attributePaths = {
            "produto",
            "localDestino",
            "responsavel"
    })
    Optional<OrdemProducao> findById(Long id);
}