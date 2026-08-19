package br.com.inova.sigin.bootstrap;

import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.usuario.entity.Usuario;
import br.com.inova.sigin.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.time.LocalDateTime;

import br.com.inova.sigin.usuario.entity.Perfil;
import br.com.inova.sigin.usuario.entity.UsuarioPerfil;
import br.com.inova.sigin.usuario.repository.PerfilRepository;
import br.com.inova.sigin.usuario.repository.UsuarioPerfilRepository;

@Component
@RequiredArgsConstructor
public class DeliveryIntegrationBootstrap implements CommandLineRunner {

    private static final String LOGIN = "delivery-integration";
    private static final String PREFIXO_SENHA = "SIGIN_DELIVERY_";
    private static final Path SECRET_FILE =
            Path.of("..", "sigin-runtime", "secrets", "delivery.properties");

    private final UsuarioRepository usuarioRepository;
    private final PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;
    private final PerfilRepository perfilRepository;
    private final UsuarioPerfilRepository usuarioPerfilRepository;

    @Override
    @Transactional
    public void run(String... args) {

        Usuario usuario = usuarioRepository
                .findByLoginAndAtivoTrue(LOGIN)
                .orElse(null);

        if (usuario == null) {
            usuario = criarUsuario();
        }

        Perfil perfil = perfilRepository.findByAtivoTrue()
                .stream()
                .filter(p -> "DELIVERY_INTEGRATION".equalsIgnoreCase(p.getNome()))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Perfil DELIVERY_INTEGRATION não encontrado."
                        )
                );

        if (!usuarioPerfilRepository.existsByUsuarioIdAndPerfilId(
                usuario.getId(),
                perfil.getId()
        )) {
            usuarioPerfilRepository.save(
                    UsuarioPerfil.builder()
                            .usuario(usuario)
                            .perfil(perfil)
                            .build()
            );
        }

        if (!segredoValido()) {
            String senha = gerarSenha();

            usuario.setSenha(passwordEncoder.encode(senha));
            usuarioRepository.save(usuario);

            gravarSegredo(senha);
        }
    }

    private Usuario criarUsuario() {
        Pessoa pessoa = Pessoa.builder()
                .nome("Integração Delivery")
                .tipoDocumento("OUTRO")
                .documento(LOGIN)
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        pessoa = pessoaRepository.save(pessoa);

        Usuario usuario = Usuario.builder()
                .pessoa(pessoa)
                .login(LOGIN)
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        return usuarioRepository.save(usuario);
    }

    private String gerarSenha() {
        byte[] bytes = new byte[16];
        new SecureRandom().nextBytes(bytes);

        StringBuilder senha = new StringBuilder(PREFIXO_SENHA);

        for (byte value : bytes) {
            senha.append(String.format("%02x", value));
        }

        return senha.toString();
    }

    private boolean segredoValido() {
        if (!Files.exists(SECRET_FILE)) {
            return false;
        }

        try {
            String conteudo = Files.readString(SECRET_FILE);

            return conteudo.lines()
                    .anyMatch(linha ->
                            linha.startsWith("SIGIN_CORE_LOGIN=")
                                    && !linha.substring("SIGIN_CORE_LOGIN=".length()).isBlank())
                    &&
                    conteudo.lines()
                            .anyMatch(linha ->
                                    linha.startsWith("SIGIN_CORE_SENHA=")
                                            && !linha.substring("SIGIN_CORE_SENHA=".length()).isBlank());

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Não foi possível ler o segredo do Delivery.",
                    e
            );
        }
    }

    private void gravarSegredo(String senha) {
        try {
            Files.createDirectories(SECRET_FILE.getParent());

            String conteudo =
                    "SIGIN_CORE_LOGIN=" + LOGIN + System.lineSeparator()
                            + "SIGIN_CORE_SENHA=" + senha + System.lineSeparator();

            Files.writeString(SECRET_FILE, conteudo);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Não foi possível gravar o segredo do Delivery.",
                    e
            );
        }
    }
}