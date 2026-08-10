package br.com.inova.sigin.usuario.auth.controller;

import br.com.inova.sigin.usuario.auth.dto.AuthMeResponse;
import br.com.inova.sigin.usuario.auth.dto.LoginRequest;
import br.com.inova.sigin.usuario.auth.dto.LoginResponse;
import br.com.inova.sigin.usuario.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public AuthMeResponse me() {
        return authService.me();
    }
}