package br.com.inova.sigin.dev.seed;

import br.com.inova.sigin.material.entity.Material;
import br.com.inova.sigin.material.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MaterialSeeder implements Seeder {

    private final MaterialRepository repository;

    @Override
    public String getNome() {
        return "Material";
    }

    @Override
    public void executar() {

        criar(
                "MAT000001",
                "PLA Preto",
                "Filamento PLA Preto",
                "KG",
                "1.000"
        );

        criar(
                "MAT000002",
                "PLA Branco",
                "Filamento PLA Branco",
                "KG",
                "1.000"
        );

    }

    private void criar(
            String codigo,
            String nome,
            String descricao,
            String unidade,
            String estoqueMinimo
    ) {

        if (repository.existsByCodigo(codigo)) {
            return;
        }

        Material material = Material.builder()
                .codigo(codigo)
                .nome(nome)
                .descricao(descricao)
                .unidadeMedida(unidade)
                .estoqueMinimo(new BigDecimal(estoqueMinimo))
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        repository.save(material);
    }
}