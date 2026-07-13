package br.com.inova.sigin.reservaestoque.service;

import br.com.inova.sigin.estoque.service.EstoqueService;
import br.com.inova.sigin.material.repository.MaterialRepository;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.produtomaterial.entity.ProdutoMaterial;
import br.com.inova.sigin.produtomaterial.repository.ProdutoMaterialRepository;
import br.com.inova.sigin.reservaestoque.entity.ReservaEstoque;
import br.com.inova.sigin.reservaestoque.mapper.ReservaEstoqueMapper;
import br.com.inova.sigin.reservaestoque.repository.ReservaEstoqueRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaEstoqueService {

    private final ReservaEstoqueRepository repository;
    private final ReservaEstoqueMapper mapper;
    private final ProdutoMaterialRepository produtoMaterialRepository;
    private final MaterialRepository materialRepository;
    private final OrdemProducaoRepository ordemProducaoRepository;
    private final EstoqueService estoqueService;

    public void reservarMateriais(
            OrdemProducao ordemProducao) {

        List<ProdutoMaterial> materiais =
                produtoMaterialRepository.findByProdutoId(
                        ordemProducao.getProduto().getId());

        for (ProdutoMaterial produtoMaterial : materiais) {

            BigDecimal quantidadeNecessaria =
                    produtoMaterial.getQuantidade()
                            .multiply(
                                    ordemProducao.getQuantidadePlanejada());

            BigDecimal disponivel =
                    estoqueService.calcularDisponivel(
                            produtoMaterial.getMaterial().getId());

            if (disponivel.compareTo(
                    quantidadeNecessaria) < 0) {

                throw new RegraNegocioException(
                        "Material "
                                + produtoMaterial.getMaterial().getNome()
                                + " insuficiente. Disponível: "
                                + disponivel
                                + " Necessário: "
                                + quantidadeNecessaria
                );
            }

            ReservaEstoque reserva =
                    ReservaEstoque.builder()
                            .material(produtoMaterial.getMaterial())
                            .ordemProducao(ordemProducao)
                            .quantidade(quantidadeNecessaria)
                            .dataReserva(LocalDateTime.now())
                            .ativo(true)
                            .build();

            repository.save(reserva);
        }
    }
}