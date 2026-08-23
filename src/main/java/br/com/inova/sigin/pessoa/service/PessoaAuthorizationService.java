package br.com.inova.sigin.pessoa.service;

import br.com.inova.sigin.usuario.entity.Usuario;
import br.com.inova.sigin.usuario.entity.UsuarioPerfil;
import br.com.inova.sigin.usuario.repository.UsuarioPerfilRepository;
import br.com.inova.sigin.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaAuthorizationService {

    private static final String PERFIL_CLIENTE = "CLIENTE";
    private static final String PREFIXO_PERMISSAO_PESSOA = "PESSOA";

    private final UsuarioRepository usuarioRepository;
    private final UsuarioPerfilRepository usuarioPerfilRepository;

    public void verificarAcessoPessoa(
            Long pessoaId,
            Authentication authentication
    ) {
        Usuario usuario = buscarUsuario(authentication);

        if (ehCliente(usuario)) {
            if (usuario.getPessoa() == null
                    || usuario.getPessoa().getId() == null
                    || !usuario.getPessoa().getId().equals(pessoaId)) {

                throw new AccessDeniedException("Acesso negado");
            }

            return;
        }

        if (!possuiPermissaoPessoa(usuario)) {
            throw new AccessDeniedException("Acesso negado");
        }
    }

    public void verificarAcessoAdministrativo(
            Authentication authentication
    ) {
        Usuario usuario = buscarUsuario(authentication);

        if (ehCliente(usuario)) {
            throw new AccessDeniedException("Acesso negado");
        }

        if (!possuiPermissaoPessoa(usuario)) {
            throw new AccessDeniedException("Acesso negado");
        }
    }

    public boolean ehCliente(Usuario usuario) {

        List<UsuarioPerfil> perfis = buscarPerfis(usuario.getId());

        boolean possuiPerfilCliente = perfis.stream()
                .anyMatch(up ->
                        up.getPerfil() != null
                                && up.getPerfil().getNome() != null
                                && PERFIL_CLIENTE.equalsIgnoreCase(
                                up.getPerfil().getNome()
                        )
                );

        boolean possuiPerfilInterno = perfis.stream()
                .anyMatch(up ->
                        up.getPerfil() != null
                                && up.getPerfil().getNome() != null
                                && !PERFIL_CLIENTE.equalsIgnoreCase(
                                up.getPerfil().getNome()
                        )
                );

        return possuiPerfilCliente && !possuiPerfilInterno;
    }

    private boolean possuiPermissaoPessoa(Usuario usuario) {

        return buscarPerfis(usuario.getId())
                .stream()
                .filter(up -> up.getPerfil() != null)
                .flatMap(up ->
                        up.getPerfil()
                                .getPermissoes()
                                .stream()
                )
                .filter(pp -> pp.getPermissao() != null)
                .anyMatch(pp ->
                        Boolean.TRUE.equals(
                                pp.getPermissao().getAtivo()
                        )
                                && pp.getPermissao().getCodigo() != null
                                && ehPermissaoPessoa(
                                pp.getPermissao().getCodigo()
                        )
                );
    }

    private boolean ehPermissaoPessoa(String codigo) {

        return codigo.equalsIgnoreCase(
                PREFIXO_PERMISSAO_PESSOA
        )
                || codigo.toUpperCase().startsWith(
                PREFIXO_PERMISSAO_PESSOA + "_"
        );
    }

    private List<UsuarioPerfil> buscarPerfis(Long usuarioId) {

        return usuarioPerfilRepository
                .buscarComPermissoesPorUsuario(usuarioId);
    }

    private Usuario buscarUsuario(
            Authentication authentication
    ) {
        if (authentication == null
                || authentication.getName() == null
                || authentication.getName().isBlank()) {

            throw new AccessDeniedException(
                    "Acesso negado"
            );
        }

        return usuarioRepository
                .findByLoginAndAtivoTrue(
                        authentication.getName()
                )
                .orElseThrow(() ->
                        new AccessDeniedException(
                                "Acesso negado"
                        )
                );
    }

    public boolean ehCliente(
            Authentication authentication
    ) {
        return ehCliente(
                buscarUsuario(authentication)
        );
    }
}