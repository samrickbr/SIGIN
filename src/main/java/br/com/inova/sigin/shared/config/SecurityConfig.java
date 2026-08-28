package br.com.inova.sigin.shared.config;

import br.com.inova.sigin.shared.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> {
                })

                .formLogin(form -> form.disable())

                .httpBasic(httpBasic -> httpBasic.disable())

                .logout(logout -> logout.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(
                                (request, response, authException) ->
                                        response.sendError(
                                                HttpServletResponse.SC_UNAUTHORIZED
                                        )
                        )
                        .accessDeniedHandler(
                                (request, response, accessDeniedException) ->
                                        response.sendError(
                                                HttpServletResponse.SC_FORBIDDEN
                                        )
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        /*
                         * Autenticação e documentação
                         */
                        .requestMatchers(
                                "/auth/login",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/ceps/**",
                                "/estados"
                        ).permitAll()

                        /*
                         * Catálogo público existente.
                         */
                        .requestMatchers(
                                "/api/catalogo/**"
                        ).permitAll()

                        /*
                         * Autocadastro do cliente.
                         *
                         * Mantém POST /api/delivery/clientes público.
                         */
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/delivery/clientes"
                        ).permitAll()

                        /*
                         * Pesquisa operacional de clientes.
                         */
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/delivery/clientes"
                        ).authenticated()

                        /*
                         * Cadastro operacional de cliente.
                         */
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/delivery/clientes/operacional"
                        ).authenticated()

                        /*
                         * Endereço operacional de cliente.
                         */
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/delivery/clientes/*/enderecos"
                        ).authenticated()

                        /*
                         * Demais endpoints existentes.
                         */
                        .requestMatchers(HttpMethod.OPTIONS, "/**")
                        .permitAll()

                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
