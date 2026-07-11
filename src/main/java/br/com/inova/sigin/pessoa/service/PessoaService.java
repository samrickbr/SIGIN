package br.com.inova.sigin.pessoa.service;

import br.com.inova.sigin.pessoa.dto.PessoaRequest;
import br.com.inova.sigin.pessoa.dto.PessoaResponse;
import br.com.inova.sigin.pessoa.dto.PessoaUpdateRequest;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.shared.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;


    public PessoaResponse criar(PessoaRequest request) {

        Pessoa pessoa = Pessoa.builder()
                .nome(StringUtil.normalizarNome(request.getNome()))
                .tipoDocumento(request.getTipoDocumento())
                .documento(request.getDocumento())
                .telefone(request.getTelefone())
                .email(request.getEmail())
                .observacao(request.getObservacao())
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        return converter(repository.save(pessoa));
    }


    public List<PessoaResponse> listar() {

        return repository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }


    public PessoaResponse buscarPorId(Long id) {

        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Pessoa não encontrada")
                );

        return converter(pessoa);
    }


    public PessoaResponse atualizar(Long id, PessoaUpdateRequest request) {

        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Pessoa não encontrada")
                );


        if (request.getNome() != null) {
            pessoa.setNome(
                    StringUtil.normalizarNome(request.getNome())
            );
        }

        if (request.getTelefone() != null) {
            pessoa.setTelefone(request.getTelefone());
        }

        if (request.getEmail() != null) {
            pessoa.setEmail(request.getEmail());
        }

        if (request.getObservacao() != null) {
            pessoa.setObservacao(request.getObservacao());
        }

        if (request.getAtivo() != null) {
            pessoa.setAtivo(request.getAtivo());
        }


        return converter(repository.save(pessoa));
    }


    public void excluir(Long id) {

        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Pessoa não encontrada")
                );

        pessoa.setAtivo(false);

        repository.save(pessoa);
    }

    private PessoaResponse converter(Pessoa pessoa) {

        return PessoaResponse.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .tipoDocumento(pessoa.getTipoDocumento())
                .documento(pessoa.getDocumento())
                .telefone(pessoa.getTelefone())
                .email(pessoa.getEmail())
                .observacao(pessoa.getObservacao())
                .ativo(pessoa.getAtivo())
                .dataCriacao(pessoa.getDataCriacao())
                .tipos(
                        pessoa.getTipos()
                                .stream()
                                .map(pt -> pt.getTipoPessoa().getNome())
                                .toList()
                )
                .build();
    }
}