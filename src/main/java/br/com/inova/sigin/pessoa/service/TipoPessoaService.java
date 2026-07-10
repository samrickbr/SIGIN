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

        TipoPessoa tipo = TipoPessoa.builder()
                .nome(request.getNome().toUpperCase())
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