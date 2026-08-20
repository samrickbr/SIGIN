package br.com.inova.sigin.usuario.auth.service;

import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.usuario.auth.dto.LoginRequest;
import br.com.inova.sigin.usuario.auth.dto.LoginResponse;
import br.com.inova.sigin.usuario.entity.Usuario;
import br.com.inova.sigin.usuario.entity.UsuarioPerfil;
import br.com.inova.sigin.usuario.repository.UsuarioPerfilRepository;
import br.com.inova.sigin.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import br.com.inova.sigin.pessoa.dto.PessoaResponse;
import br.com.inova.sigin.usuario.dto.PerfilResponse;
import br.com.inova.sigin.usuario.dto.PermissaoResponse;
import br.com.inova.sigin.usuario.auth.dto.AuthMeResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UsuarioPerfilRepository usuarioPerfilRepository;

    public LoginResponse login(LoginRequest request) {

        Usuario usuario = usuarioRepository.findByLoginAndAtivoTrue(request.getLogin())
                .orElseThrow(() -> new RegraNegocioException("Login ou senha inválidos."));

        if (!Boolean.TRUE.equals(usuario.getAtivo())) {
            throw new RegraNegocioException("Usuário inativo.");
        }

        if (!passwordEncoder.matches(request.getSenha(), usuario.getSenha())) {
            throw new RegraNegocioException("Login ou senha inválidos.");
        }

        usuario.setUltimoLogin(LocalDateTime.now());
        usuarioRepository.save(usuario);

        return LoginResponse.builder()
                .token(jwtService.gerarToken(usuario.getLogin()))
                .clienteId(usuario.getPessoa().getId())
                .build();
    }

    @Transactional(readOnly = true)
    public AuthMeResponse me() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String login = authentication.getName();

        Usuario usuario = usuarioRepository
                .findByLoginAndAtivoTrue(login)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Usuário autenticado não encontrado."
                        )
                );

        List<UsuarioPerfil> usuarioPerfis =
                usuarioPerfilRepository
                        .buscarComPermissoesPorUsuario(usuario.getId());

        List<PerfilResponse> perfis = usuarioPerfis.stream()
                .map(UsuarioPerfil::getPerfil)
                .distinct()
                .map(perfil -> PerfilResponse.builder()
                        .id(perfil.getId())
                        .nome(perfil.getNome())
                        .descricao(perfil.getDescricao())
                        .ativo(perfil.getAtivo())
                        .build())
                .toList();

        List<PermissaoResponse> permissoes = usuarioPerfis.stream()
                .flatMap(usuarioPerfil ->
                        usuarioPerfil.getPerfil()
                                .getPermissoes()
                                .stream()
                )
                .map(perfilPermissao -> perfilPermissao.getPermissao())
                .distinct()
                .map(permissao -> new PermissaoResponse(
                        permissao.getId(),
                        permissao.getCodigo(),
                        permissao.getDescricao(),
                        permissao.getAtivo()
                ))
                .toList();

        PessoaResponse pessoa = PessoaResponse.builder()
                .id(usuario.getPessoa().getId())
                .nome(usuario.getPessoa().getNome())
                .tipoDocumento(usuario.getPessoa().getTipoDocumento())
                .documento(usuario.getPessoa().getDocumento())
                .telefone(usuario.getPessoa().getTelefone())
                .email(usuario.getPessoa().getEmail())
                .observacao(usuario.getPessoa().getObservacao())
                .ativo(usuario.getPessoa().getAtivo())
                .dataCriacao(usuario.getPessoa().getDataCriacao())
                .tipos(usuario.getPessoa().getTipos()
                        .stream()
                        .map(pessoaTipo -> pessoaTipo.getTipoPessoa().getNome())
                        .toList())
                .build();

        return AuthMeResponse.builder()
                .id(usuario.getId())
                .login(usuario.getLogin())
                .ativo(usuario.getAtivo())
                .pessoa(pessoa)
                .perfis(perfis)
                .permissoes(permissoes)
                .build();
    }
}