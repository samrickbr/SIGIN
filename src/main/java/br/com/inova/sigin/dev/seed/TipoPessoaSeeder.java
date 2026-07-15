package br.com.inova.sigin.dev.seed;

import br.com.inova.sigin.pessoa.entity.TipoPessoa;
import br.com.inova.sigin.pessoa.repository.TipoPessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TipoPessoaSeeder implements Seeder {

    private final TipoPessoaRepository repository;

    @Override
    public String getNome() {
        return "TipoPessoa";
    }

    @Override
    public void executar() {

        criar("CLIENTE", "Pessoa cliente do sistema");
        criar("FORNECEDOR", "Pessoa fornecedora");
        criar("FUNCIONARIO", "Funcionário");

    }

    private void criar(String nome, String descricao) {

        if (repository.existsByNomeIgnoreCase(nome)) {
            return;
        }

        TipoPessoa tipo = TipoPessoa.builder()
                .nome(nome)
                .descricao(descricao)
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        repository.save(tipo);
    }
}