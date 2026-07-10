package br.com.inova.sigin.pessoa.service;

import br.com.inova.sigin.pessoa.dto.PessoaTipoRequest;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.entity.PessoaTipo;
import br.com.inova.sigin.pessoa.entity.TipoPessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.pessoa.repository.PessoaTipoRepository;
import br.com.inova.sigin.pessoa.repository.TipoPessoaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PessoaTipoService {

    private final PessoaRepository pessoaRepository;
    private final TipoPessoaRepository tipoPessoaRepository;
    private final PessoaTipoRepository pessoaTipoRepository;


    public void adicionarTipo(Long pessoaId, PessoaTipoRequest request) {

        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() ->
                        new RegraNegocioException("Pessoa não encontrada")
                );


        TipoPessoa tipoPessoa = tipoPessoaRepository.findById(request.getTipoPessoaId())
                .orElseThrow(() ->
                        new RegraNegocioException("Tipo de pessoa não encontrado")
                );


        PessoaTipo pessoaTipo = PessoaTipo.builder()
                .pessoa(pessoa)
                .tipoPessoa(tipoPessoa)
                .dataCriacao(LocalDateTime.now())
                .build();


        pessoaTipoRepository.save(pessoaTipo);
    }
}