package br.com.inova.sigin.local.service;

import br.com.inova.sigin.local.dto.LocalRequest;
import br.com.inova.sigin.local.dto.LocalResponse;
import br.com.inova.sigin.local.dto.LocalUpdateRequest;
import br.com.inova.sigin.local.entity.Local;
import br.com.inova.sigin.local.repository.LocalRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.shared.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocalService {

    private final LocalRepository repository;

    @Transactional
    public LocalResponse salvar(LocalRequest request) {

        if (repository.existsByNomeIgnoreCase(request.getNome())) {
            throw new IllegalArgumentException("Já existe um local com esse nome.");
        }

        Local local = Local.builder()
                .nome(StringUtil.normalizarNome(request.getNome()))
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        repository.save(local);

        return toResponse(local);
    }

    public List<LocalResponse> listar() {
        return repository.findByAtivoTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public LocalResponse buscarPorId(Long id) {

        Local local = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Local não encontrado."));

        return toResponse(local);
    }

    @Transactional
    public LocalResponse atualizar(Long id, LocalUpdateRequest request) {

        Local local = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Local não encontrado."));

        local.setNome(StringUtil.normalizarNome(request.getNome()));

        if (request.getAtivo() != null) {
            local.setAtivo(request.getAtivo());
        }

        repository.save(local);

        return toResponse(local);
    }

    @Transactional
    public void excluir(Long id) {

        Local local = repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Local não encontrado."));

        local.setAtivo(false);

        repository.save(local);
    }

    private LocalResponse toResponse(Local local) {

        return LocalResponse.builder()
                .id(local.getId())
                .nome(local.getNome())
                .ativo(local.getAtivo())
                .dataCriacao(local.getDataCriacao())
                .build();
    }
}