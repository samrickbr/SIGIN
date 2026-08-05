package br.com.inova.sigin.usuario.service;

import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.usuario.dto.PermissaoRequest;
import br.com.inova.sigin.usuario.dto.PermissaoResponse;
import br.com.inova.sigin.usuario.entity.Permissao;
import br.com.inova.sigin.usuario.mapper.PermissaoMapper;
import br.com.inova.sigin.usuario.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissaoService {

    private final PermissaoRepository repository;
    private final PermissaoMapper mapper;


    public PermissaoResponse criar(PermissaoRequest request) {

        repository.findByCodigoIgnoreCase(request.codigo())
                .ifPresent(p -> {
                    throw new RegraNegocioException(
                            "Código de permissão já cadastrado."
                    );
                });

        Permissao permissao = Permissao.builder()
                .codigo(request.codigo())
                .descricao(request.descricao())
                .ativo(request.ativo() != null ? request.ativo() : true)
                .build();

        return mapper.toResponse(repository.save(permissao));
    }


    public List<PermissaoResponse> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public PermissaoResponse buscar(Long id) {

        return mapper.toResponse(buscarEntidade(id));
    }

    public PermissaoResponse atualizar(Long id, PermissaoRequest request) {

        Permissao permissao = buscarEntidade(id);

        permissao.setCodigo(request.codigo());
        permissao.setDescricao(request.descricao());

        if (request.ativo() != null) {
            permissao.setAtivo(request.ativo());
        }

        return mapper.toResponse(repository.save(permissao));
    }

    public void excluir(Long id) {

        Permissao permissao = buscarEntidade(id);

        permissao.setAtivo(false);

        repository.save(permissao);
    }

    private Permissao buscarEntidade(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Permissão não encontrada."
                        )
                );
    }
}