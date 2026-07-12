package br.com.inova.sigin.produtomaterial.service;

import br.com.inova.sigin.material.entity.Material;
import br.com.inova.sigin.material.repository.MaterialRepository;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.produtomaterial.dto.ProdutoMaterialRequest;
import br.com.inova.sigin.produtomaterial.dto.ProdutoMaterialResponse;
import br.com.inova.sigin.produtomaterial.dto.ProdutoMaterialUpdateRequest;
import br.com.inova.sigin.produtomaterial.entity.ProdutoMaterial;
import br.com.inova.sigin.produtomaterial.mapper.ProdutoMaterialMapper;
import br.com.inova.sigin.produtomaterial.repository.ProdutoMaterialRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoMaterialService {

    private final ProdutoMaterialRepository repository;
    private final ProdutoRepository produtoRepository;
    private final MaterialRepository materialRepository;
    private final ProdutoMaterialMapper mapper;

    public ProdutoMaterialResponse criar(ProdutoMaterialRequest request) {

        if (repository.existsByProduto_IdAndMaterial_Id(
                request.getProdutoId(),
                request.getMaterialId())) {

            throw new RegraNegocioException(
                    "Este material já está vinculado ao produto."
            );
        }

        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() ->
                        new RegraNegocioException("Produto não encontrado"));

        Material material = materialRepository.findById(request.getMaterialId())
                .orElseThrow(() ->
                        new RegraNegocioException("Material não encontrado"));

        ProdutoMaterial entity = ProdutoMaterial.builder()
                .produto(produto)
                .material(material)
                .quantidade(request.getQuantidade())
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        return mapper.toResponse(repository.save(entity));
    }

    public List<ProdutoMaterialResponse> listarPorProduto(Long produtoId) {

        return repository.findByProdutoId(produtoId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ProdutoMaterialResponse atualizar(Long id, ProdutoMaterialUpdateRequest request) {

        ProdutoMaterial entity = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Registro não encontrado"));

        if (request.getQuantidade() != null) {
            entity.setQuantidade(request.getQuantidade());
        }

        if (request.getAtivo() != null) {
            entity.setAtivo(request.getAtivo());
        }

        return mapper.toResponse(repository.save(entity));
    }

    public void excluir(Long id) {

        ProdutoMaterial entity = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Registro não encontrado"));

        entity.setAtivo(false);

        repository.save(entity);
    }
}