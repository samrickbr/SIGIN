package br.com.inova.sigin.dev.seed;

import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PessoaSeeder implements Seeder {

    private final PessoaRepository repository;

    @Override
    public String getNome() {
        return "Pessoa";
    }

    @Override
    public void executar() {

        if (repository.existsByNomeIgnoreCase("Cliente Teste")) {
            return;
        }

        Pessoa pessoa = Pessoa.builder()
                .nome("Cliente Teste")
                .tipoDocumento("CPF")
                .documento("00000000000")
                .telefone("(00) 00000-0000")
                .email("cliente@sigin.dev")
                .observacao("Registro criado automaticamente pelo Seeder")
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        repository.save(pessoa);
    }
}