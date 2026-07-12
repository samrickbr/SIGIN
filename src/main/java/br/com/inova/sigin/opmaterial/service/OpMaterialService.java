package br.com.inova.sigin.opmaterial.service;

import br.com.inova.sigin.material.entity.Material;
import br.com.inova.sigin.material.repository.MaterialRepository;
import br.com.inova.sigin.opmaterial.dto.OpMaterialRequest;
import br.com.inova.sigin.opmaterial.dto.OpMaterialResponse;
import br.com.inova.sigin.opmaterial.dto.OpMaterialUpdateRequest;
import br.com.inova.sigin.opmaterial.entity.OpMaterial;
import br.com.inova.sigin.opmaterial.mapper.OpMaterialMapper;
import br.com.inova.sigin.opmaterial.repository.OpMaterialRepository;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpMaterialService {

    private final OpMaterialRepository repository;
    private final OrdemProducaoRepository ordemProducaoRepository;
    private final MaterialRepository materialRepository;
    private final OpMaterialMapper mapper;
    public OpMaterialResponse criar(OpMaterialRequest request) {

        if (repository.existsByOrdemProducao_IdAndMaterial_Id(
                request.getOrdemProducaoId(),
                request.getMaterialId())) {
            throw new RegraNegocioException(
                    "Este material já está vinculado à ordem de produção."
            );
        }

        OrdemProducao ordemProducao =
                ordemProducaoRepository.findById(request.getOrdemProducaoId())
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Ordem de produção não encontrada"
                                ));

        Material material =
                materialRepository.findById(request.getMaterialId())
                        .orElseThrow(() ->
                                new RegraNegocioException(
                                        "Material não encontrado"
                                ));

        OpMaterial entity = OpMaterial.builder()
                .ordemProducao(ordemProducao)
                .material(material)
                .quantidadePlanejada(request.getQuantidadePlanejada())
                .quantidadeConsumida(java.math.BigDecimal.ZERO)
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        return mapper.toResponse(repository.save(entity));
    }

    public List<OpMaterialResponse> listarPorOp(Long ordemProducaoId) {

        return repository.findByOrdemProducaoId(ordemProducaoId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public OpMaterialResponse atualizar(
            Long id,
            OpMaterialUpdateRequest request) {

        OpMaterial entity = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Material da OP não encontrado"
                        ));

        if (request.getQuantidadePlanejada() != null) {
            entity.setQuantidadePlanejada(
                    request.getQuantidadePlanejada()
            );
        }

        if (request.getQuantidadeConsumida() != null) {
            entity.setQuantidadeConsumida(
                    request.getQuantidadeConsumida()
            );
        }

        if (request.getAtivo() != null) {
            entity.setAtivo(request.getAtivo());
        }
        return mapper.toResponse(repository.save(entity));
    }
    public void excluir(Long id) {

        OpMaterial entity = repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Material da OP não encontrado"
                        ));
        entity.setAtivo(false);
        repository.save(entity);
    }
}