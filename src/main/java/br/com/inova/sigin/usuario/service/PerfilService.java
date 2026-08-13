package br.com.inova.sigin.usuario.service;

import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.usuario.dto.PerfilRequest;
import br.com.inova.sigin.usuario.dto.PerfilResponse;
import br.com.inova.sigin.usuario.entity.Perfil;
import br.com.inova.sigin.usuario.mapper.PerfilMapper;
import br.com.inova.sigin.usuario.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository repository;
    private final PerfilMapper mapper;

    public PerfilResponse criar(PerfilRequest request) {

        if (repository.existsByNome(request.getNome())) {
            throw new RegraNegocioException("Perfil já cadastrado.");
        }

        Perfil perfil = Perfil.builder()
                .nome(request.getNome())
                .descricao(request.getDescricao())
                .ativo(request.getAtivo())
                .build();

        return mapper.toResponse(repository.save(perfil));
    }

    public List<PerfilResponse> listar(Boolean ativo) {
        if (ativo == null || ativo) {
            return repository.findByAtivoTrue()
                    .stream()
                    .map(mapper::toResponse)
                    .toList();
        }

        return repository.findByAtivoFalse()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public PerfilResponse buscar(Long id) {
        return mapper.toResponse(buscaPerfil(id));
    }

    public PerfilResponse atualizar(Long id, PerfilRequest request) {

        Perfil perfil = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Perfil não encontrado.")
                );

        if (!perfil.getNome().equals(request.getNome())
                && repository.existsByNome(request.getNome())) {
            throw new RegraNegocioException("Perfil já cadastrado.");
        }

        perfil.setNome(request.getNome());
        perfil.setDescricao(request.getDescricao());
        perfil.setAtivo(request.getAtivo());

        return mapper.toResponse(repository.save(perfil));
    }

    public void excluir(Long id) {

        Perfil perfil = buscaPerfil(id);

        perfil.setAtivo(false);

        repository.save(perfil);
    }

    private Perfil buscaPerfil(Long id) {

        return repository.findByIdAndAtivoTrue(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Perfil não encontrado."));
    }
}