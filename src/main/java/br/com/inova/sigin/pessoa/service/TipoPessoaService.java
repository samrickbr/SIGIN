package br.com.inova.sigin.pessoa.service;

import br.com.inova.sigin.pessoa.dto.TipoPessoaRequest;
import br.com.inova.sigin.pessoa.dto.TipoPessoaResponse;
import br.com.inova.sigin.pessoa.entity.TipoPessoa;
import br.com.inova.sigin.pessoa.repository.TipoPessoaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoPessoaService {

    private final TipoPessoaRepository repository;

    public TipoPessoaResponse criar(TipoPessoaRequest request) {

        String nome = normalizarNome(request.getNome());

        if (repository.existsByNomeIgnoreCase(nome)) {
            throw new RegraNegocioException(
                    "Já existe um tipo de pessoa com esse nome."
            );
        }

        TipoPessoa tipo = TipoPessoa.builder()
                .nome(nome)
                .descricao(request.getDescricao())
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        return converter(repository.save(tipo));
    }

    public List<TipoPessoaResponse> listar() {
        return repository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    public TipoPessoaResponse buscarPorId(Long id) {
        return converter(buscarEntidade(id));
    }

    public TipoPessoaResponse atualizar(
            Long id,
            TipoPessoaRequest request
    ) {
        TipoPessoa tipo = buscarEntidade(id);

        String nome = normalizarNome(request.getNome());

        if (!tipo.getNome().equalsIgnoreCase(nome)
                && repository.existsByNomeIgnoreCase(nome)) {
            throw new RegraNegocioException(
                    "Já existe um tipo de pessoa com esse nome."
            );
        }

        tipo.setNome(nome);
        tipo.setDescricao(request.getDescricao());

        return converter(repository.save(tipo));
    }

    public TipoPessoaResponse alterarAtivo(
            Long id,
            boolean ativo
    ) {
        TipoPessoa tipo = buscarEntidade(id);

        tipo.setAtivo(ativo);

        return converter(repository.save(tipo));
    }

    private TipoPessoa buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Tipo de pessoa não encontrado."
                        )
                );
    }

    private String normalizarNome(String nome) {
        return nome.trim().toUpperCase();
    }

    private TipoPessoaResponse converter(TipoPessoa tipo) {
        return TipoPessoaResponse.builder()
                .id(tipo.getId())
                .nome(tipo.getNome())
                .descricao(tipo.getDescricao())
                .ativo(tipo.getAtivo())
                .dataCriacao(tipo.getDataCriacao())
                .build();
    }
}