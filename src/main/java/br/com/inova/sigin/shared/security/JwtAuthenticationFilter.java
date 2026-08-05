package br.com.inova.sigin.shared.security;

import br.com.inova.sigin.usuario.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;
    private static final long EXPIRACAO =
            1000L * 60 * 60 * 24 * 7; // 7 dias

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("===== JWT FILTER =====");
        final String authHeader = request.getHeader("Authorization");
        System.out.println("HEADER: " + authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(7);
        System.out.println("TOKEN: " + token);
        String login;
        try {
            login = jwtService.obterLogin(token);
            System.out.println("LOGIN: " + login);
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }
        if (login != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(login);
            System.out.println("USER: " + userDetails.getUsername());

            System.out.println("VALIDO: " + jwtService.tokenValido(token));

            if (jwtService.tokenValido(token)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
                System.out.println("AUTENTICADO: " + authentication.getName());
                System.out.println(authentication.getAuthorities());
            }
        }
        filterChain.doFilter(request, response);
    }
}