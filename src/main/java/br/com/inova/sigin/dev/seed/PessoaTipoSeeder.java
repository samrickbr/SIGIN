package br.com.inova.sigin.dev.seed;

import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.entity.PessoaTipo;
import br.com.inova.sigin.pessoa.entity.TipoPessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.pessoa.repository.PessoaTipoRepository;
import br.com.inova.sigin.pessoa.repository.TipoPessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PessoaTipoSeeder implements Seeder {

    private final PessoaRepository pessoaRepository;
    private final TipoPessoaRepository tipoPessoaRepository;
    private final PessoaTipoRepository repository;

    @Override
    public String getNome() {
        return "PessoaTipo";
    }

    @Override
    public void executar() {

        Pessoa pessoa = pessoaRepository
                .findByNomeIgnoreCase("Cliente Teste")
                .orElse(null);

        TipoPessoa tipo = tipoPessoaRepository
                .findByNomeIgnoreCase("CLIENTE")
                .orElse(null);

        if (pessoa == null || tipo == null) {
            return;
        }

        if (repository.existsByPessoaIdAndTipoPessoaId(
                pessoa.getId(),
                tipo.getId()
        )) {
            return;
        }

        PessoaTipo pessoaTipo = PessoaTipo.builder()
                .pessoa(pessoa)
                .tipoPessoa(tipo)
                .dataCriacao(LocalDateTime.now())
                .build();

        repository.save(pessoaTipo);
    }
}