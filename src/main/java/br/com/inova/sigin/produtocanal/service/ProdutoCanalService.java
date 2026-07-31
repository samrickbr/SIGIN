package br.com.inova.sigin.produtocanal.service;

import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import br.com.inova.sigin.canalvenda.repository.CanalVendaRepository;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.produtocanal.dto.ProdutoCanalRequest;
import br.com.inova.sigin.produtocanal.dto.ProdutoCanalResponse;
import br.com.inova.sigin.produtocanal.entity.ProdutoCanal;
import br.com.inova.sigin.produtocanal.mapper.ProdutoCanalMapper;
import br.com.inova.sigin.produtocanal.repository.ProdutoCanalRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoCanalService {

    private final ProdutoCanalRepository repository;
    private final ProdutoRepository produtoRepository;
    private final CanalVendaRepository canalVendaRepository;

    @Transactional
    public ProdutoCanalResponse cadastrar(ProdutoCanalRequest request) {

        validarDuplicidade(request.getProdutoId(), request.getCanalVendaId());

        Produto produto = buscarProduto(request.getProdutoId());
        CanalVenda canalVenda = buscarCanalVenda(request.getCanalVendaId());

        ProdutoCanal entity = ProdutoCanalMapper.toEntity(request, produto, canalVenda);

        return ProdutoCanalMapper.toResponse(repository.save(entity));
    }

    @Transactional
    public ProdutoCanalResponse atualizar(Long id, ProdutoCanalRequest request) {

        ProdutoCanal entity = buscarEntidade(id);

        if (!entity.getProduto().getId().equals(request.getProdutoId())
                || !entity.getCanalVenda().getId().equals(request.getCanalVendaId())) {

            validarDuplicidade(request.getProdutoId(), request.getCanalVendaId());
        }

        Produto produto = buscarProduto(request.getProdutoId());
        CanalVenda canalVenda = buscarCanalVenda(request.getCanalVendaId());

        ProdutoCanalMapper.updateEntity(entity, request, produto, canalVenda);

        return ProdutoCanalMapper.toResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<ProdutoCanalResponse> listar() {

        return repository.findAll()
                .stream()
                .map(ProdutoCanalMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProdutoCanalResponse buscarPorId(Long id) {

        return ProdutoCanalMapper.toResponse(buscarEntidade(id));
    }

    @Transactional
    public void excluir(Long id) {

        repository.delete(buscarEntidade(id));
    }

    private ProdutoCanal buscarEntidade(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Produto x Canal não encontrado."));
    }

    private Produto buscarProduto(Long id) {

        return produtoRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Produto não encontrado."));
    }

    private CanalVenda buscarCanalVenda(Long id) {

        return canalVendaRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Canal de venda não encontrado."));
    }

    private void validarDuplicidade(Long produtoId, Long canalVendaId) {

        if (repository.existsByProdutoIdAndCanalVendaId(produtoId, canalVendaId)) {
            throw new RegraNegocioException("Produto já vinculado a este canal.");
        }
    }
    @Transactional(readOnly = true)
    public void validarProdutoDisponivelNoCanal(
            Long produtoId,
            Long canalVendaId) {

        if (!repository.existsByProdutoIdAndCanalVendaIdAndAtivoTrue(
                produtoId,
                canalVendaId)) {

            throw new RegraNegocioException(
                    "Produto não disponível para este canal."
            );
        }
    }
}