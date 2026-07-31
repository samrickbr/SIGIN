package br.com.inova.sigin.dev.seed;

import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import br.com.inova.sigin.canalvenda.repository.CanalVendaRepository;
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
    private final CanalVendaRepository canalVendaRepository;

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
                .isEmpty()) {
            return;
        }
        CanalVenda canalVenda = canalVendaRepository.findById(1L)
                .orElseThrow(() ->
                        new RuntimeException("Canal venda não encontrado")
                );
        ProdutoVenda venda = ProdutoVenda.builder()
                .produto(produto)
                .canalVenda(canalVenda)
                .precoVenda(new BigDecimal(preco))
                .imagem(imagem)
                .disponivelVenda(true)
                .build();
        produtoVendaRepository.save(venda);
    }
}