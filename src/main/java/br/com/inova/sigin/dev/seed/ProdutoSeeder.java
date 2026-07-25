package br.com.inova.sigin.dev.seed;

import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@Order(5)
@RequiredArgsConstructor
public class ProdutoSeeder implements Seeder {

    private final ProdutoRepository repository;

    @Override
    public String getNome() {
        return "Produto";
    }

    @Override
    public void executar() {

        criar(
                "PROD000003",
                "Caixa 3D Porta Figurinhas",
                "Produto Copa 2026",
                BigDecimal.valueOf(29.90)
        );

        criar(
                "PROD000004",
                "Chaveiro Taça Copa 2026 Premium",
                "Chaveiro personalizado",
                BigDecimal.valueOf(9.90)
        );
    }

    private void criar(
            String codigo,
            String nome,
            String descricao,
            BigDecimal precoVenda
    ) {

        Produto produto;

        if (repository.existsByCodigo(codigo)) {

            produto = repository.findByCodigo(codigo)
                    .orElseThrow();

            produto.setPrecoVenda(precoVenda);
            produto.setDisponivelVenda(true);

        } else {

            produto = Produto.builder()
                    .codigo(codigo)
                    .nome(nome)
                    .descricao(descricao)
                    .precoVenda(precoVenda)
                    .disponivelVenda(true)
                    .ativo(true)
                    .dataCriacao(LocalDateTime.now())
                    .build();
        }

        repository.save(produto);
    }
}