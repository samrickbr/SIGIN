package br.com.inova.sigin.apontamentoproducao.repository;

import br.com.inova.sigin.apontamentoproducao.entity.ApontamentoProducao;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApontamentoProducaoRepository extends JpaRepository<ApontamentoProducao, Long> {


    @EntityGraph(attributePaths = {
            "ordemProducao",
            "responsavel"
    })
    List<ApontamentoProducao> findByOrdemProducaoId(Long ordemProducaoId);


    @EntityGraph(attributePaths = {
            "ordemProducao",
            "responsavel"
    })
    Optional<ApontamentoProducao> findById(Long id);

}