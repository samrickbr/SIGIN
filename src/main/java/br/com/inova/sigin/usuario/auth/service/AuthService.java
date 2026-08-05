package br.com.inova.sigin.usuario.auth.service;

import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.usuario.auth.dto.LoginRequest;
import br.com.inova.sigin.usuario.auth.dto.LoginResponse;
import br.com.inova.sigin.usuario.entity.Usuario;
import br.com.inova.sigin.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

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
                .build();
    }
}