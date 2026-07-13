package br.com.inova.sigin.estoque.repository;

import br.com.inova.sigin.movimentacaoestoque.entity.MovimentacaoEstoque;
import br.com.inova.sigin.movimentacaoestoque.repository.MovimentacaoEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EstoqueRepository {

    private final MovimentacaoEstoqueRepository movimentacaoRepository;


    public List<MovimentacaoEstoque> buscarMovimentacoesPorMaterial(
            Long materialId) {

        return movimentacaoRepository
                .findByMaterialId(materialId);
    }
}