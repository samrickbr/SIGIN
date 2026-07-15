package br.com.inova.sigin.dev.seed;

import br.com.inova.sigin.material.entity.Material;
import br.com.inova.sigin.material.repository.MaterialRepository;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.produtomaterial.entity.ProdutoMaterial;
import br.com.inova.sigin.produtomaterial.repository.ProdutoMaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ProdutoMaterialSeeder implements Seeder {

    private final ProdutoRepository produtoRepository;
    private final MaterialRepository materialRepository;
    private final ProdutoMaterialRepository repository;

    @Override
    public String getNome() {
        return "ProdutoMaterial";
    }

    @Override
    public void executar() {

        Produto caixa = produtoRepository.findByCodigo("PROD000003")
                .orElse(null);

        Produto chaveiro = produtoRepository.findByCodigo("PROD000004")
                .orElse(null);

        Material plaPreto = materialRepository.findByCodigo("MAT000001")
                .orElse(null);

        if (caixa != null && plaPreto != null) {
            criar(caixa, plaPreto, "0.080");
        }

        if (chaveiro != null && plaPreto != null) {
            criar(chaveiro, plaPreto, "0.018");
        }
    }

    private void criar(
            Produto produto,
            Material material,
            String quantidade
    ) {

        if (repository.existsByProduto_IdAndMaterial_Id(
                produto.getId(),
                material.getId()
        )) {
            return;
        }

        ProdutoMaterial item = ProdutoMaterial.builder()
                .produto(produto)
                .material(material)
                .quantidade(new BigDecimal(quantidade))
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        repository.save(item);
    }
}