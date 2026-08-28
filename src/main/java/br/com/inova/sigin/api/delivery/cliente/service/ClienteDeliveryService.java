package br.com.inova.sigin.api.delivery.cliente.service;

import br.com.inova.sigin.api.delivery.cliente.dto.ClientePesquisaResponse;
import br.com.inova.sigin.api.delivery.cliente.dto.ClienteRequest;
import br.com.inova.sigin.api.delivery.cliente.dto.ClienteResponse;
import br.com.inova.sigin.pessoa.dto.PessoaEnderecoRequest;
import br.com.inova.sigin.pessoa.dto.PessoaEnderecoResponse;
import br.com.inova.sigin.pessoa.dto.PessoaRequest;
import br.com.inova.sigin.pessoa.dto.PessoaResponse;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.pessoa.service.PessoaEnderecoService;
import br.com.inova.sigin.pessoa.service.PessoaService;
import br.com.inova.sigin.pessoa.service.PessoaTipoService;
import br.com.inova.sigin.usuario.dto.UsuarioRequest;
import br.com.inova.sigin.usuario.entity.Usuario;
import br.com.inova.sigin.usuario.repository.PerfilRepository;
import br.com.inova.sigin.usuario.repository.UsuarioRepository;
import br.com.inova.sigin.usuario.service.UsuarioPerfilService;
import br.com.inova.sigin.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.api.delivery.cliente.dto.ClienteOperacionalRequest;
import br.com.inova.sigin.api.delivery.cliente.dto.ClienteOperacionalResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteDeliveryService {

    private final PessoaRepository pessoaRepository;
    private final PessoaService pessoaService;
    private final PessoaTipoService pessoaTipoService;
    private final UsuarioService usuarioService;
    private final UsuarioPerfilService usuarioPerfilService;
    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    private final PessoaEnderecoService pessoaEnderecoService;


    @Transactional(readOnly = true)
    public List<ClientePesquisaResponse> pesquisar(String busca) {

        if (busca == null || busca.isBlank()) {
            return List.of();
        }

        String termo = busca.trim();

        return pessoaRepository
                .pesquisarClientes(termo)
                .stream()
                .map(this::converterPesquisa)
                .toList();
    }

    public ClienteResponse buscarPorTelefone(String telefone) {

        Pessoa pessoa = pessoaRepository.findByTelefone(telefone)
                .orElseThrow();

        return converter(pessoa);
    }

    public ClienteResponse buscarPorDocumento(String documento) {

        Pessoa pessoa = pessoaRepository.findByDocumento(documento)
                .orElseThrow();

        return converter(pessoa);
    }

    @Transactional
    public ClienteResponse criar(ClienteRequest request) {

        Pessoa pessoaExistente = pessoaRepository
                .findByDocumento(request.getDocumento())
                .orElseGet(() ->
                        pessoaRepository
                                .findByTelefone(request.getTelefone())
                                .orElse(null)
                );

        if (pessoaExistente != null) {

            pessoaTipoService.adicionarTipoCliente(
                    pessoaExistente.getId()
            );

            if (!usuarioRepository.existsByPessoaId(pessoaExistente.getId())) {

                UsuarioRequest usuarioRequest = new UsuarioRequest();

                usuarioRequest.setPessoaId(pessoaExistente.getId());
                usuarioRequest.setLogin(request.getDocumento());
                usuarioRequest.setSenha(request.getSenha());
                usuarioRequest.setAtivo(true);

                var usuario = usuarioService.criar(usuarioRequest);

                Long perfilClienteId = perfilRepository.findAll()
                        .stream()
                        .filter(perfil -> Boolean.TRUE.equals(perfil.getAtivo()))
                        .filter(perfil -> "CLIENTE".equalsIgnoreCase(perfil.getNome()))
                        .map(perfil -> perfil.getId())
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Perfil CLIENTE não encontrado."
                                )
                        );

                usuarioPerfilService.adicionarPerfil(
                        usuario.getId(),
                        perfilClienteId
                );
            }

            return converter(pessoaExistente);
        }

        PessoaRequest pessoaRequest = new PessoaRequest();

        pessoaRequest.setNome(request.getNome());
        pessoaRequest.setTipoDocumento("CPF");
        pessoaRequest.setDocumento(request.getDocumento());
        pessoaRequest.setTelefone(request.getTelefone());
        pessoaRequest.setEmail(request.getEmail());

        PessoaResponse pessoa = pessoaService.criar(pessoaRequest);

        pessoaTipoService.adicionarTipoCliente(pessoa.getId());

        UsuarioRequest usuarioRequest = new UsuarioRequest();

        usuarioRequest.setPessoaId(pessoa.getId());
        usuarioRequest.setLogin(request.getDocumento());
        usuarioRequest.setSenha(request.getSenha());
        usuarioRequest.setAtivo(true);

        var usuario = usuarioService.criar(usuarioRequest);

        Long perfilClienteId = perfilRepository.findAll()
                .stream()
                .filter(perfil -> Boolean.TRUE.equals(perfil.getAtivo()))
                .filter(perfil -> "CLIENTE".equalsIgnoreCase(perfil.getNome()))
                .map(perfil -> perfil.getId())
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Perfil CLIENTE não encontrado."
                        )
                );

        usuarioPerfilService.adicionarPerfil(
                usuario.getId(),
                perfilClienteId
        );

        return ClienteResponse.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .telefone(pessoa.getTelefone())
                .email(pessoa.getEmail())
                .build();
    }

    private ClientePesquisaResponse converterPesquisa(Pessoa pessoa) {

        return ClientePesquisaResponse.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .telefone(pessoa.getTelefone())
                .documento(pessoa.getDocumento())
                .email(pessoa.getEmail())
                .build();
    }

    private ClienteResponse converter(Pessoa pessoa) {

        return ClienteResponse.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .telefone(pessoa.getTelefone())
                .email(pessoa.getEmail())
                .build();
    }

    private Long obterPessoaId(Authentication authentication) {

        Usuario usuario = usuarioRepository
                .findByLoginAndAtivoTrue(authentication.getName())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Usuário autenticado não encontrado."
                        )
                );

        return usuario.getPessoa().getId();
    }

    public List<PessoaEnderecoResponse> listarEnderecos(
            Authentication authentication
    ) {
        return pessoaEnderecoService.listar(
                obterPessoaId(authentication)
        );
    }

    public PessoaEnderecoResponse criarEndereco(
            Authentication authentication,
            PessoaEnderecoRequest request
    ) {
        return pessoaEnderecoService.criar(
                obterPessoaId(authentication),
                request
        );
    }

    public PessoaEnderecoResponse buscarEndereco(
            Authentication authentication,
            Long enderecoId
    ) {
        return pessoaEnderecoService.buscarPorId(
                obterPessoaId(authentication),
                enderecoId
        );
    }

    public PessoaEnderecoResponse atualizarEndereco(
            Authentication authentication,
            Long enderecoId,
            PessoaEnderecoRequest request
    ) {
        return pessoaEnderecoService.atualizar(
                obterPessoaId(authentication),
                enderecoId,
                request
        );
    }

    public PessoaEnderecoResponse definirEnderecoPrincipal(
            Authentication authentication,
            Long enderecoId
    ) {
        return pessoaEnderecoService.definirPrincipal(
                obterPessoaId(authentication),
                enderecoId
        );
    }

    public void excluirEndereco(
            Authentication authentication,
            Long enderecoId
    ) {
        pessoaEnderecoService.excluir(
                obterPessoaId(authentication),
                enderecoId
        );
    }
    @Transactional
    public ClienteOperacionalResponse criarOperacional(
            ClienteOperacionalRequest request
    ) {

        if (request.getDocumento() != null
                && !request.getDocumento().isBlank()
                && pessoaRepository.findByDocumento(
                request.getDocumento().trim()
        ).isPresent()) {

            throw new RegraNegocioException(
                    "Já existe uma pessoa cadastrada com este documento."
            );
        }

        if (request.getTelefone() != null
                && !request.getTelefone().isBlank()
                && pessoaRepository.findByTelefone(
                request.getTelefone().trim()
        ).isPresent()) {

            throw new RegraNegocioException(
                    "Já existe uma pessoa cadastrada com este telefone."
            );
        }

        PessoaRequest pessoaRequest = new PessoaRequest();

        pessoaRequest.setNome(request.getNome());
        pessoaRequest.setTipoDocumento("CPF");
        pessoaRequest.setDocumento(
                request.getDocumento()
        );
        pessoaRequest.setTelefone(
                request.getTelefone()
        );
        pessoaRequest.setEmail(
                request.getEmail()
        );

        PessoaResponse pessoa = pessoaService.criar(
                pessoaRequest
        );

        pessoaTipoService.adicionarTipoCliente(
                pessoa.getId()
        );

        return ClienteOperacionalResponse.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .documento(pessoa.getDocumento())
                .telefone(pessoa.getTelefone())
                .email(pessoa.getEmail())
                .build();
    }
    @Transactional
    public PessoaEnderecoResponse criarEnderecoOperacional(
            Long clienteId,
            PessoaEnderecoRequest request
    ) {
        Pessoa pessoa = pessoaRepository.findById(clienteId)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Cliente não encontrado."
                        )
                );

        boolean ehCliente = pessoa.getTipos()
                .stream()
                .anyMatch(tipo ->
                        "CLIENTE".equalsIgnoreCase(
                                tipo.getTipoPessoa().getNome()
                        )
                );

        if (!ehCliente) {
            throw new RegraNegocioException(
                    "A pessoa informada não é um cliente."
            );
        }

        return pessoaEnderecoService.criar(
                clienteId,
                request
        );
    }

}
