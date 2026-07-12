package br.com.inova.sigin.movimentacaoestoque.repository;

import br.com.inova.sigin.movimentacaoestoque.entity.MovimentacaoEstoque;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovimentacaoEstoqueRepository extends JpaRepository<MovimentacaoEstoque, Long> {


    @EntityGraph(attributePaths = {
            "produto",
            "material",
            "local",
            "responsavel"
    })
    List<MovimentacaoEstoque> findAll();


    @EntityGraph(attributePaths = {
            "produto",
            "material",
            "local",
            "responsavel"
    })
    Optional<MovimentacaoEstoque> findById(Long id);


    @EntityGraph(attributePaths = {
            "produto",
            "material",
            "local",
            "responsavel"
    })
    List<MovimentacaoEstoque> findByProdutoId(Long produtoId);


    @EntityGraph(attributePaths = {
            "produto",
            "material",
            "local",
            "responsavel"
    })
    List<MovimentacaoEstoque> findByMaterialId(Long materialId);

}