package br.com.inova.sigin.produtomaterial.repository;

import br.com.inova.sigin.produtomaterial.entity.ProdutoMaterial;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoMaterialRepository extends JpaRepository<ProdutoMaterial, Long> {

    List<ProdutoMaterial> findByProdutoIdAndAtivoTrue(Long produtoId);
    @EntityGraph(attributePaths = {"produto", "material"})
    List<ProdutoMaterial> findByProdutoId(Long produtoId);

    @EntityGraph(attributePaths = {"produto", "material"})
    Optional<ProdutoMaterial> findById(Long id);

    boolean existsByProduto_IdAndMaterial_Id(Long produtoId, Long materialId);
}