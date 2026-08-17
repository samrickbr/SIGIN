package br.com.inova.sigin.produtovenda.service;

import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import br.com.inova.sigin.canalvenda.repository.CanalVendaRepository;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.produtocanal.service.ProdutoCanalService;
import br.com.inova.sigin.produtovenda.dto.ProdutoVendaRequest;
import br.com.inova.sigin.produtovenda.dto.ProdutoVendaResponse;
import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;
import br.com.inova.sigin.produtovenda.mapper.ProdutoVendaMapper;
import br.com.inova.sigin.produtovenda.repository.ProdutoVendaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoVendaService {

    private final ProdutoVendaRepository repository;
    private final ProdutoRepository produtoRepository;
    private final CanalVendaRepository canalVendaRepository;
    private final ProdutoCanalService produtoCanalService;
    @Transactional
    public ProdutoVendaResponse cadastrar(
            ProdutoVendaRequest request) {

        validarDuplicidade(
                request.getProdutoId(),
                request.getCanalVendaId()
        );

        Produto produto = buscarProduto(request.getProdutoId());
        CanalVenda canalVenda = buscarCanalVenda(request.getCanalVendaId());

        ProdutoVenda entity =
                ProdutoVendaMapper.toEntity(request, produto, canalVenda);

        return ProdutoVendaMapper.toResponse(
                repository.save(entity)
        );
    }


    @Transactional(readOnly = true)
    public List<ProdutoVendaResponse> listar() {

        return repository.findAll()
                .stream()
                .map(ProdutoVendaMapper::toResponse)
                .toList();
    }


    @Transactional(readOnly = true)
    public ProdutoVendaResponse buscarPorId(Long id) {

        return ProdutoVendaMapper.toResponse(
                buscarEntidade(id)
        );
    }


    @Transactional
    public ProdutoVendaResponse atualizar(
            Long id,
            ProdutoVendaRequest request) {

        ProdutoVenda entity = buscarEntidade(id);

        if (!entity.getProduto().getId().equals(request.getProdutoId())
                || !entity.getCanalVenda().getId().equals(request.getCanalVendaId())) {

            validarDuplicidade(
                    request.getProdutoId(),
                    request.getCanalVendaId()
            );
        }

        Produto produto = buscarProduto(request.getProdutoId());
        CanalVenda canalVenda = buscarCanalVenda(request.getCanalVendaId());

        ProdutoVendaMapper.updateEntity(
                entity,
                request,
                produto,
                canalVenda
        );

        return ProdutoVendaMapper.toResponse(
                repository.save(entity)
        );
    }


    @Transactional
    public void excluir(Long id) {

        repository.delete(buscarEntidade(id));
    }


    private ProdutoVenda buscarEntidade(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Produto venda não encontrado."
                        ));
    }


    private Produto buscarProduto(Long id) {

        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Produto não encontrado."
                        ));
    }


    private CanalVenda buscarCanalVenda(Long id) {

        return canalVendaRepository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Canal de venda não encontrado."
                        ));
    }


    private void validarDuplicidade(
            Long produtoId,
            Long canalVendaId) {

        if (repository.existsByProdutoIdAndCanalVendaId(
                produtoId,
                canalVendaId)) {

            throw new RegraNegocioException(
                    "Produto já cadastrado para este canal."
            );
        }
    }

    @Transactional(readOnly = true)
    public List<ProdutoVendaResponse> listarDisponiveis() {

        return repository.findProdutosDisponiveis()
                .stream()
                .map(ProdutoVendaMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProdutoVenda> listarDisponiveisEntity() {

        return repository.findProdutosDisponiveis();
    }

    @Transactional(readOnly = true)
    public ProdutoVenda obterProdutoDisponivel(
            Long produtoId,
            Long canalVendaId) {

        produtoCanalService.validarProdutoDisponivelNoCanal(
                produtoId,
                canalVendaId
        );

        ProdutoVenda produtoVenda = repository
                .findByProdutoIdAndCanalVendaId(
                        produtoId,
                        canalVendaId
                )
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Produto sem cadastro comercial para este canal."
                        )
                );

        if (!Boolean.TRUE.equals(produtoVenda.getDisponivelVenda())) {
            throw new RegraNegocioException(
                    "Produto indisponível para venda neste canal."
            );
        }

        return produtoVenda;
    }
    @Transactional(readOnly = true)
    public List<ProdutoVenda> listarDisponiveisPorCanal(
            Long canalVendaId
    ) {
        return repository.findCatalogoPorCanal(canalVendaId);
    }
}