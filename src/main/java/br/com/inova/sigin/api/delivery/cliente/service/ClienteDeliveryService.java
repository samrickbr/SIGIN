package br.com.inova.sigin.api.delivery.cliente.service;

import br.com.inova.sigin.api.delivery.cliente.dto.ClienteRequest;
import br.com.inova.sigin.api.delivery.cliente.dto.ClienteResponse;
import br.com.inova.sigin.pessoa.dto.PessoaRequest;
import br.com.inova.sigin.pessoa.dto.PessoaResponse;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.pessoa.service.PessoaService;
import br.com.inova.sigin.pessoa.service.PessoaTipoService;
import br.com.inova.sigin.usuario.dto.UsuarioRequest;
import br.com.inova.sigin.usuario.repository.PerfilRepository;
import br.com.inova.sigin.usuario.repository.UsuarioRepository;
import br.com.inova.sigin.usuario.service.UsuarioPerfilService;
import br.com.inova.sigin.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ClienteResponse buscarPorTelefone(String telefone) {

        Pessoa pessoa = pessoaRepository.findByTelefone(telefone)
                .orElseThrow();

        pessoaTipoService.adicionarTipoCliente(pessoa.getId());

        return converter(pessoa);
    }

    public ClienteResponse buscarPorDocumento(String documento) {

        Pessoa pessoa = pessoaRepository.findByDocumento(documento)
                .orElseThrow();

        pessoaTipoService.adicionarTipoCliente(pessoa.getId());

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
                                new IllegalStateException("Perfil CLIENTE nÃ£o encontrado.")
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
                        new IllegalStateException("Perfil CLIENTE nÃ£o encontrado.")
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

    private ClienteResponse converter(Pessoa pessoa) {

        return ClienteResponse.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .telefone(pessoa.getTelefone())
                .email(pessoa.getEmail())
                .build();
    }
}