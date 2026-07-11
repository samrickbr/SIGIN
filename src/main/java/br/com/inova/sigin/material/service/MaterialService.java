package br.com.inova.sigin.material.service;

import br.com.inova.sigin.material.dto.MaterialRequest;
import br.com.inova.sigin.material.dto.MaterialResponse;
import br.com.inova.sigin.material.dto.MaterialUpdateRequest;
import br.com.inova.sigin.material.entity.Material;
import br.com.inova.sigin.material.mapper.MaterialMapper;
import br.com.inova.sigin.material.repository.MaterialRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialService {

    private final MaterialRepository repository;
    private final MaterialMapper mapper;


    public MaterialResponse criar(MaterialRequest request) {

        if (repository.existsByCodigo(request.getCodigo().toUpperCase())) {
            throw new RegraNegocioException("Código de material já cadastrado");
        }

        Material material = mapper.toEntity(request);

        return mapper.toResponse(repository.save(material));
    }


    public List<MaterialResponse> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    public MaterialResponse buscarPorId(Long id) {

        Material material = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Material não encontrado")
                );

        return mapper.toResponse(material);
    }


    public MaterialResponse atualizar(Long id, MaterialUpdateRequest request) {

        Material material = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Material não encontrado")
                );


        if (request.getNome() != null) {
            material.setNome(request.getNome());
        }

        if (request.getDescricao() != null) {
            material.setDescricao(request.getDescricao());
        }

        if (request.getUnidadeMedida() != null) {
            material.setUnidadeMedida(
                    request.getUnidadeMedida().toUpperCase()
            );
        }

        if (request.getEstoqueMinimo() != null) {
            material.setEstoqueMinimo(request.getEstoqueMinimo());
        }

        if (request.getAtivo() != null) {
            material.setAtivo(request.getAtivo());
        }


        return mapper.toResponse(repository.save(material));
    }


    public void excluir(Long id) {

        Material material = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Material não encontrado")
                );

        material.setAtivo(false);

        repository.save(material);
    }
}