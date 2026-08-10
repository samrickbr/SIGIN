package br.com.inova.sigin.usuario.auth;

import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.shared.BaseIntegrationTest;
import br.com.inova.sigin.usuario.auth.dto.LoginRequest;
import br.com.inova.sigin.usuario.auth.dto.LoginResponse;
import br.com.inova.sigin.usuario.entity.Perfil;
import br.com.inova.sigin.usuario.entity.Permissao;
import br.com.inova.sigin.usuario.entity.PerfilPermissao;
import br.com.inova.sigin.usuario.entity.Usuario;
import br.com.inova.sigin.usuario.entity.UsuarioPerfil;
import br.com.inova.sigin.usuario.repository.PerfilPermissaoRepository;
import br.com.inova.sigin.usuario.repository.PerfilRepository;
import br.com.inova.sigin.usuario.repository.PermissaoRepository;
import br.com.inova.sigin.usuario.repository.UsuarioPerfilRepository;
import br.com.inova.sigin.usuario.repository.UsuarioRepository;
import br.com.inova.sigin.usuario.auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class AuthMeIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @Autowired
    private UsuarioPerfilRepository usuarioPerfilRepository;

    @Autowired
    private PerfilPermissaoRepository perfilPermissaoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String LOGIN = "teste.auth";
    private static final String SENHA = "123456";

    @BeforeEach
    void prepararUsuario() {

        usuarioRepository.findByLoginAndAtivoTrue(LOGIN)
                .ifPresent(usuario -> {
                    usuarioPerfilRepository
                            .deleteAll(
                                    usuarioPerfilRepository
                                            .findAll()
                                            .stream()
                                            .filter(up ->
                                                    up.getUsuario()
                                                            .getId()
                                                            .equals(usuario.getId()))
                                            .toList()
                            );

                    usuarioRepository.delete(usuario);
                });

        Pessoa pessoa = pessoaRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow();

        Usuario usuario = Usuario.builder()
                .pessoa(pessoa)
                .login(LOGIN)
                .senha(passwordEncoder.encode(SENHA))
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        usuario = usuarioRepository.save(usuario);

        Permissao permissao = permissaoRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() ->
                        permissaoRepository.save(
                                Permissao.builder()
                                        .codigo("TESTE_AUTH_ME")
                                        .descricao("Permissão para teste de /auth/me")
                                        .ativo(true)
                                        .build()
                        )
                );

        Perfil perfil = perfilRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() ->
                        perfilRepository.save(
                                Perfil.builder()
                                        .nome("TESTE_AUTH_ME")
                                        .descricao("Perfil para teste de /auth/me")
                                        .ativo(true)
                                        .build()
                        )
                );

        PerfilPermissao perfilPermissao =
                PerfilPermissao.builder()
                        .perfil(perfil)
                        .permissao(permissao)
                        .build();

        perfilPermissaoRepository.save(perfilPermissao);

        usuarioPerfilRepository.save(
                UsuarioPerfil.builder()
                        .usuario(usuario)
                        .perfil(perfil)
                        .build()
        );
    }

    @Test
    void deveRetornarIdentidadeDoUsuarioAutenticado() throws Exception {

        String response = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                            "login": "teste.auth",
                                            "senha": "123456"
                                        }
                                        """)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = com.jayway.jsonpath.JsonPath
                .parse(response)
                .read("$.token", String.class);

        mockMvc.perform(
                        get("/auth/me")
                                .header("Authorization", "Bearer " + token)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.login").value(LOGIN))
                .andExpect(jsonPath("$.ativo").value(true))
                .andExpect(jsonPath("$.pessoa").exists())
                .andExpect(jsonPath("$.pessoa.id").isNumber())
                .andExpect(jsonPath("$.perfis").isArray())
                .andExpect(jsonPath("$.perfis[0].nome").isString())
                .andExpect(jsonPath("$.permissoes").isArray())
                .andExpect(jsonPath("$.permissoes[0].codigo").isString());
    }

    @Test
    void deveRetornar401SemToken() throws Exception {

        mockMvc.perform(
                        get("/auth/me")
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deveRetornar401ComTokenInvalido() throws Exception {

        mockMvc.perform(
                        get("/auth/me")
                                .header(
                                        "Authorization",
                                        "Bearer token-invalido"
                                )
                )
                .andExpect(status().isUnauthorized());
    }
}