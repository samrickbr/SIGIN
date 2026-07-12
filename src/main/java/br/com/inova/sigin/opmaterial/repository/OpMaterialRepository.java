package br.com.inova.sigin.opmaterial.repository;

import br.com.inova.sigin.opmaterial.entity.OpMaterial;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OpMaterialRepository extends JpaRepository<OpMaterial, Long> {

    @EntityGraph(attributePaths = {
            "ordemProducao",
            "material"
    })
    List<OpMaterial> findByOrdemProducaoId(Long ordemProducaoId);


    @EntityGraph(attributePaths = {
            "ordemProducao",
            "material"
    })
    Optional<OpMaterial> findById(Long id);


    boolean existsByOrdemProducao_IdAndMaterial_Id(
            Long ordemProducaoId,
            Long materialId
    );
}