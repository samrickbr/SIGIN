package br.com.inova.sigin.pessoa.service;

import br.com.inova.sigin.pessoa.dto.PessoaEnderecoResponse;
import br.com.inova.sigin.pessoa.dto.PessoaRequest;
import br.com.inova.sigin.pessoa.dto.PessoaResponse;
import br.com.inova.sigin.pessoa.dto.PessoaUpdateRequest;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaEnderecoRepository;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.shared.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;
    private final PessoaEnderecoRepository enderecoRepository;
    private final PessoaAuthorizationService authorizationService;

    public PessoaResponse criar(
            PessoaRequest request,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoAdministrativo(
                authentication
        );

        return criarPessoa(request);
    }

    /**
     * Uso interno por serviços do próprio Core.
     *
     * A autorização deve ocorrer no fluxo que expõe a operação
     * externamente. Este método preserva os consumidores internos
     * existentes, como o fluxo de criação de cliente do Delivery.
     */
    public PessoaResponse criar(
            PessoaRequest request
    ) {
        return criarPessoa(request);
    }

    private PessoaResponse criarPessoa(
            PessoaRequest request
    ) {
        Pessoa pessoa = Pessoa.builder()
                .nome(
                        StringUtil.normalizarNome(
                                request.getNome()
                        )
                )
                .tipoDocumento(
                        request.getTipoDocumento()
                )
                .documento(
                        request.getDocumento()
                )
                .telefone(
                        request.getTelefone()
                )
                .email(
                        request.getEmail()
                )
                .observacao(
                        request.getObservacao()
                )
                .ativo(true)
                .dataCriacao(
                        LocalDateTime.now()
                )
                .build();

        return converter(
                repository.save(pessoa)
        );
    }

    public List<PessoaResponse> listar(
            Authentication authentication
    ) {
        authorizationService.verificarAcessoAdministrativo(
                authentication
        );

        return repository.findAll()
                .stream()
                .map(this::converter)
                .toList();
    }

    public PessoaResponse buscarPorId(
            Long id,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoPessoa(
                id,
                authentication
        );

        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Pessoa não encontrada"
                        )
                );

        return converter(pessoa);
    }

    @Transactional
    public PessoaResponse atualizar(
            Long id,
            PessoaUpdateRequest request,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoPessoa(
                id,
                authentication
        );

        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Pessoa não encontrada"
                        )
                );

        boolean cliente =
                authorizationService.ehCliente(
                        authentication
                );

        if (request.getNome() != null) {
            pessoa.setNome(
                    StringUtil.normalizarNome(
                            request.getNome()
                    )
            );
        }

        if (request.getTelefone() != null) {
            pessoa.setTelefone(
                    request.getTelefone()
            );
        }

        if (request.getEmail() != null) {
            pessoa.setEmail(
                    request.getEmail()
            );
        }

        if (!cliente
                && request.getObservacao() != null) {

            pessoa.setObservacao(
                    request.getObservacao()
            );
        }

        if (!cliente
                && request.getAtivo() != null) {

            pessoa.setAtivo(
                    request.getAtivo()
            );
        }

        repository.save(pessoa);

        return converter(pessoa);
    }

    public void excluir(
            Long id,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoAdministrativo(
                authentication
        );

        Pessoa pessoa = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Pessoa não encontrada"
                        )
                );

        pessoa.setAtivo(false);

        repository.save(pessoa);
    }

    @Transactional(readOnly = true)
    public PessoaResponse buscarPorDocumento(
            String documento,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoAdministrativo(
                authentication
        );

        Pessoa pessoa = repository.findByDocumento(documento)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Pessoa não encontrada"
                        )
                );

        return converter(pessoa);
    }

    @Transactional(readOnly = true)
    public PessoaResponse buscarPorTelefone(
            String telefone,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoAdministrativo(
                authentication
        );

        Pessoa pessoa = repository.findByTelefone(telefone)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Pessoa não encontrada"
                        )
                );

        return converter(pessoa);
    }

    @Transactional(readOnly = true)
    public List<PessoaEnderecoResponse> listarEnderecos(
            Long pessoaId,
            Authentication authentication
    ) {
        authorizationService.verificarAcessoPessoa(
                pessoaId,
                authentication
        );

        repository.findById(pessoaId)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Pessoa não encontrada"
                        )
                );

        return enderecoRepository
                .findByPessoaIdAndAtivoTrueOrderByPrincipalDescIdAsc(
                        pessoaId
                )
                .stream()
                .map(PessoaEnderecoResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PessoaResponse buscarConsumidorFinal() {
        Pessoa pessoa = repository.buscarConsumidorFinal()
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Consumidor Final não encontrado"
                        )
                );

        return converter(pessoa);
    }
    private PessoaResponse converter(
            Pessoa pessoa
    ) {
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
                                .map(pt ->
                                        pt.getTipoPessoa().getNome()
                                )
                                .toList()
                )
                .build();
    }
}