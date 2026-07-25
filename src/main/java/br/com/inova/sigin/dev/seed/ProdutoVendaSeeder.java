package br.com.inova.sigin.dev.seed;

import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;
import br.com.inova.sigin.produtovenda.repository.ProdutoVendaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Order(6)
@RequiredArgsConstructor
public class ProdutoVendaSeeder implements Seeder {

    private final ProdutoRepository produtoRepository;
    private final ProdutoVendaRepository produtoVendaRepository;

    @Override
    public String getNome() {
        return "ProdutoVenda";
    }

    @Override
    public void executar() {

        criar(
                "Caixa 3D Porta Figurinhas",
                "29.90",
                null
        );

        criar(
                "Chaveiro Taça Copa 2026 Premium",
                "9.90",
                null
        );

    }

    private void criar(
            String nomeProduto,
            String preco,
            String imagem
    ) {

        Produto produto = produtoRepository.findByNome(nomeProduto)
                .orElse(null);


        if (produto == null) {
            return;
        }


        if (produtoVendaRepository
                .findByProdutoId(produto.getId())
                .isPresent()) {
            return;
        }


        ProdutoVenda venda = ProdutoVenda.builder()
                .produto(produto)
                .precoVenda(new BigDecimal(preco))
                .imagem(imagem)
                .disponivelVenda(true)
                .build();


        produtoVendaRepository.save(venda);
    }
}