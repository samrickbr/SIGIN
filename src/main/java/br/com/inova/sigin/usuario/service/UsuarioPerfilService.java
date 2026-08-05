package br.com.inova.sigin.usuario.service;

import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.usuario.dto.PerfilResponse;
import br.com.inova.sigin.usuario.entity.Perfil;
import br.com.inova.sigin.usuario.entity.Usuario;
import br.com.inova.sigin.usuario.entity.UsuarioPerfil;
import br.com.inova.sigin.usuario.mapper.PerfilMapper;
import br.com.inova.sigin.usuario.repository.PerfilRepository;
import br.com.inova.sigin.usuario.repository.UsuarioPerfilRepository;
import br.com.inova.sigin.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioPerfilService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final UsuarioPerfilRepository usuarioPerfilRepository;
    private final PerfilMapper perfilMapper;

    public void adicionarPerfil(Long usuarioId, Long perfilId) {

        Usuario usuario = usuarioRepository.findByIdAndAtivoTrue(usuarioId)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado."));

        Perfil perfil = perfilRepository.findByIdAndAtivoTrue(perfilId)
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado."));

        if (usuarioPerfilRepository.existsByUsuarioIdAndPerfilId(usuarioId, perfilId)) {
            throw new RegraNegocioException("Perfil já vinculado ao usuário.");
        }

        UsuarioPerfil usuarioPerfil = UsuarioPerfil.builder()
                .usuario(usuario)
                .perfil(perfil)
                .build();

        usuarioPerfilRepository.save(usuarioPerfil);
    }

    public List<PerfilResponse> listarPerfis(Long usuarioId) {

        if (!usuarioRepository.existsById(usuarioId)) {
            throw new RegraNegocioException("Usuário não encontrado.");
        }

        return usuarioPerfilRepository.buscarComPermissoesPorUsuario(usuarioId)
                .stream()
                .map(UsuarioPerfil::getPerfil)
                .map(perfilMapper::toResponse)
                .toList();
    }

    public void removerPerfil(Long usuarioId, Long perfilId) {

        UsuarioPerfil usuarioPerfil = usuarioPerfilRepository
                .buscarComPermissoesPorUsuario(usuarioId)
                .stream()
                .filter(up -> up.getPerfil().getId().equals(perfilId))
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException("Vínculo não encontrado."));

        usuarioPerfilRepository.delete(usuarioPerfil);
    }
}