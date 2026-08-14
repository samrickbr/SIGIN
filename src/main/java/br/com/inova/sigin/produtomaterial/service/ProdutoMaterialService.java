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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoMaterialService {

    private final ProdutoMaterialRepository repository;
    private final ProdutoRepository produtoRepository;
    private final MaterialRepository materialRepository;
    private final ProdutoMaterialMapper mapper;

    @Transactional
    public ProdutoMaterialResponse criar(ProdutoMaterialRequest request) {

        ProdutoMaterial existente = repository
                .findByProdutoIdAndMaterialId(
                        request.getProdutoId(),
                        request.getMaterialId()
                )
                .orElse(null);

        if (existente != null) {

            if (Boolean.TRUE.equals(existente.getAtivo())) {
                throw new RegraNegocioException(
                        "Este material já está vinculado ao produto."
                );
            }

            existente.setAtivo(true);
            existente.setQuantidade(request.getQuantidade());

            return mapper.toResponse(repository.save(existente));
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

    @Transactional
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

    @Transactional
    public void excluir(Long id) {

        ProdutoMaterial entity = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Registro não encontrado"));

        entity.setAtivo(false);

        repository.save(entity);
    }

    @Transactional
    public List<ProdutoMaterialResponse> listarPorProduto(Long produtoId) {

        return repository.findByProdutoId(produtoId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
