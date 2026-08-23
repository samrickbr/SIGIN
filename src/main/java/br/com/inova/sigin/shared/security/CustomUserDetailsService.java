package br.com.inova.sigin.shared.security;

import br.com.inova.sigin.usuario.entity.Usuario;
import br.com.inova.sigin.usuario.repository.UsuarioPerfilRepository;
import br.com.inova.sigin.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioPerfilRepository usuarioPerfilRepository;

    @Override
    public UserDetails loadUserByUsername(String login) {

        Usuario usuario = usuarioRepository
                .findByLoginAndAtivoTrue(login)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuário não encontrado."
                        )
                );

        var authorities = usuarioPerfilRepository
                .buscarCodigosPermissoesPorUsuario(usuario.getId())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .authorities(authorities)
                .build();
    }
}