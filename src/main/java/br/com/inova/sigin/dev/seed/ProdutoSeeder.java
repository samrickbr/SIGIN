package br.com.inova.sigin.dev.seed;

import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
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
                "Produto Copa 2026"
        );

        criar(
                "PROD000004",
                "Chaveiro Taça Copa 2026 Premium",
                "Chaveiro personalizado"
        );

    }

    private void criar(
            String codigo,
            String nome,
            String descricao
    ) {

        if (repository.existsByCodigo(codigo)) {
            return;
        }

        Produto produto = Produto.builder()
                .codigo(codigo)
                .nome(nome)
                .descricao(descricao)
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        repository.save(produto);
    }
}