package br.com.inova.sigin.usuario.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    // Mover para application.yml posteriormente
    private static final String SECRET =
            "sigin-core-erp-jwt-secret-key-2026-minimo-32-caracteres";

    private static final long EXPIRACAO = 1000 * 60 * 60 * 8; // 8 horas

    private final SecretKey key = Keys.hmacShaKeyFor(
            SECRET.getBytes(StandardCharsets.UTF_8));

    public String gerarToken(String login) {

        Date agora = new Date();

        Date expiracao = new Date(agora.getTime() + EXPIRACAO);

        return Jwts.builder()
                .subject(login)
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(key)
                .compact();
    }

    public String obterLogin(String token) {
        return obterClaims(token).getSubject();
    }

    public boolean tokenValido(String token) {
        return obterClaims(token)
                .getExpiration()
                .after(new Date());
    }

    private Claims obterClaims(String token) {

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}