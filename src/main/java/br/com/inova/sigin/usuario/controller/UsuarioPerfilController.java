package br.com.inova.sigin.usuario.controller;

import br.com.inova.sigin.usuario.dto.PerfilResponse;
import br.com.inova.sigin.usuario.service.UsuarioPerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioPerfilController {

    private final UsuarioPerfilService service;

    @PostMapping("/{usuarioId}/perfis/{perfilId}")
    public void adicionarPerfil(@PathVariable Long usuarioId,
                                @PathVariable Long perfilId) {

        service.adicionarPerfil(usuarioId, perfilId);
    }

    @GetMapping("/{usuarioId}/perfis")
    public List<PerfilResponse> listarPerfis(@PathVariable Long usuarioId) {

        return service.listarPerfis(usuarioId);
    }

    @DeleteMapping("/{usuarioId}/perfis/{perfilId}")
    public void removerPerfil(@PathVariable Long usuarioId,
                              @PathVariable Long perfilId) {

        service.removerPerfil(usuarioId, perfilId);
    }
}