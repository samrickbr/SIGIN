package br.com.inova.sigin.pessoa.service;

import br.com.inova.sigin.pessoa.dto.PessoaEnderecoRequest;
import br.com.inova.sigin.pessoa.dto.PessoaEnderecoResponse;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.entity.PessoaEndereco;
import br.com.inova.sigin.pessoa.repository.PessoaEnderecoRepository;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaEnderecoService {

    private final PessoaEnderecoRepository repository;
    private final PessoaRepository pessoaRepository;
    private final PessoaAuthorizationService authorizationService;

    @Transactional
    public PessoaEnderecoResponse criar(
            Long pessoaId,
            PessoaEnderecoRequest request,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoPessoa(
                pessoaId,
                authentication
        );

        return criarEndereco(
                pessoaId,
                request
        );
    }

    @Transactional
    public PessoaEnderecoResponse criar(
            Long pessoaId,
            PessoaEnderecoRequest request
    ) {
        return criarEndereco(
                pessoaId,
                request
        );
    }

    private PessoaEnderecoResponse criarEndereco(
            Long pessoaId,
            PessoaEnderecoRequest request
    ) {
        Pessoa pessoa = buscarPessoa(pessoaId);

        PessoaEndereco endereco = PessoaEndereco.builder()
                .pessoa(pessoa)
                .cep(request.getCep())
                .logradouro(request.getLogradouro())
                .numero(request.getNumero())
                .complemento(request.getComplemento())
                .bairro(request.getBairro())
                .cidade(request.getCidade())
                .uf(request.getUf())
                .principal(false)
                .ativo(true)
                .build();

        PessoaEndereco salvo = repository.save(endereco);

        if (Boolean.TRUE.equals(request.getPrincipal())
                || !repository
                .existsByPessoaIdAndAtivoTrueAndPrincipalTrue(
                        pessoaId
                )) {

            definirPrincipal(salvo);
        }

        return converter(salvo);
    }

    @Transactional(readOnly = true)
    public List<PessoaEnderecoResponse> listar(
            Long pessoaId,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoPessoa(
                pessoaId,
                authentication
        );

        return listarInterno(pessoaId);
    }

    @Transactional(readOnly = true)
    public List<PessoaEnderecoResponse> listar(
            Long pessoaId
    ) {
        return listarInterno(pessoaId);
    }

    private List<PessoaEnderecoResponse> listarInterno(
            Long pessoaId
    ) {
        buscarPessoa(pessoaId);

        return repository
                .findByPessoaIdAndAtivoTrueOrderByPrincipalDescIdAsc(
                        pessoaId
                )
                .stream()
                .map(this::converter)
                .toList();
    }

    @Transactional(readOnly = true)
    public PessoaEnderecoResponse buscarPorId(
            Long pessoaId,
            Long enderecoId,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoPessoa(
                pessoaId,
                authentication
        );

        return converter(
                buscarEndereco(
                        pessoaId,
                        enderecoId
                )
        );
    }

    @Transactional(readOnly = true)
    public PessoaEnderecoResponse buscarPorId(
            Long pessoaId,
            Long enderecoId
    ) {
        return converter(
                buscarEndereco(
                        pessoaId,
                        enderecoId
                )
        );
    }

    @Transactional
    public PessoaEnderecoResponse atualizar(
            Long pessoaId,
            Long enderecoId,
            PessoaEnderecoRequest request,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoPessoa(
                pessoaId,
                authentication
        );

        return atualizarEndereco(
                pessoaId,
                enderecoId,
                request
        );
    }

    @Transactional
    public PessoaEnderecoResponse atualizar(
            Long pessoaId,
            Long enderecoId,
            PessoaEnderecoRequest request
    ) {
        return atualizarEndereco(
                pessoaId,
                enderecoId,
                request
        );
    }

    private PessoaEnderecoResponse atualizarEndereco(
            Long pessoaId,
            Long enderecoId,
            PessoaEnderecoRequest request
    ) {
        PessoaEndereco endereco =
                buscarEndereco(
                        pessoaId,
                        enderecoId
                );

        endereco.setCep(request.getCep());
        endereco.setLogradouro(request.getLogradouro());
        endereco.setNumero(request.getNumero());
        endereco.setComplemento(request.getComplemento());
        endereco.setBairro(request.getBairro());
        endereco.setCidade(request.getCidade());
        endereco.setUf(request.getUf());

        repository.save(endereco);

        if (Boolean.TRUE.equals(request.getPrincipal())) {
            definirPrincipal(endereco);
        }

        return converter(endereco);
    }

    @Transactional
    public PessoaEnderecoResponse definirPrincipal(
            Long pessoaId,
            Long enderecoId,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoPessoa(
                pessoaId,
                authentication
        );

        return definirPrincipalInterno(
                pessoaId,
                enderecoId
        );
    }

    @Transactional
    public PessoaEnderecoResponse definirPrincipal(
            Long pessoaId,
            Long enderecoId
    ) {
        return definirPrincipalInterno(
                pessoaId,
                enderecoId
        );
    }

    private PessoaEnderecoResponse definirPrincipalInterno(
            Long pessoaId,
            Long enderecoId
    ) {
        PessoaEndereco endereco =
                buscarEndereco(
                        pessoaId,
                        enderecoId
                );

        definirPrincipal(endereco);

        return converter(endereco);
    }

    @Transactional
    public void excluir(
            Long pessoaId,
            Long enderecoId,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoPessoa(
                pessoaId,
                authentication
        );

        excluirEndereco(
                pessoaId,
                enderecoId
        );
    }

    @Transactional
    public void excluir(
            Long pessoaId,
            Long enderecoId
    ) {
        excluirEndereco(
                pessoaId,
                enderecoId
        );
    }

    private void excluirEndereco(
            Long pessoaId,
            Long enderecoId
    ) {
        PessoaEndereco endereco =
                buscarEndereco(
                        pessoaId,
                        enderecoId
                );

        boolean eraPrincipal =
                Boolean.TRUE.equals(
                        endereco.getPrincipal()
                );

        endereco.setAtivo(false);
        endereco.setPrincipal(false);

        repository.save(endereco);

        if (eraPrincipal) {
            repository
                    .findByPessoaIdAndAtivoTrueOrderByPrincipalDescIdAsc(
                            pessoaId
                    )
                    .stream()
                    .findFirst()
                    .ifPresent(this::definirPrincipal);
        }
    }

    private void definirPrincipal(
            PessoaEndereco endereco
    ) {
        Long pessoaId =
                endereco.getPessoa().getId();

        List<PessoaEndereco> enderecos =
                repository
                        .findByPessoaIdAndAtivoTrueOrderByPrincipalDescIdAsc(
                                pessoaId
                        );

        for (PessoaEndereco item : enderecos) {
            if (Boolean.TRUE.equals(
                    item.getPrincipal()
            )
                    && !item.getId().equals(
                    endereco.getId()
            )) {

                item.setPrincipal(false);

                repository.saveAndFlush(item);
            }
        }

        endereco.setPrincipal(true);

        repository.saveAndFlush(endereco);
    }

    private Pessoa buscarPessoa(
            Long pessoaId
    ) {
        return pessoaRepository.findById(pessoaId)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Pessoa não encontrada"
                        )
                );
    }

    private PessoaEndereco buscarEndereco(
            Long pessoaId,
            Long enderecoId
    ) {
        buscarPessoa(pessoaId);

        return repository
                .findByIdAndPessoaIdAndAtivoTrue(
                        enderecoId,
                        pessoaId
                )
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Endereço não encontrado"
                        )
                );
    }

    private PessoaEnderecoResponse converter(
            PessoaEndereco endereco
    ) {
        return PessoaEnderecoResponse.builder()
                .id(endereco.getId())
                .cep(endereco.getCep())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .uf(endereco.getUf())
                .principal(endereco.getPrincipal())
                .build();
    }
}