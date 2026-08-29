package br.com.inova.sigin.pedido.service;

import br.com.inova.sigin.pedido.dto.PedidoItemRequest;
import br.com.inova.sigin.pedido.dto.PedidoItemResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import br.com.inova.sigin.pedido.mapper.PedidoItemMapper;
import br.com.inova.sigin.pedido.repository.PedidoItemRepository;
import br.com.inova.sigin.pedido.repository.PedidoRepository;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;
import br.com.inova.sigin.produtovenda.service.ProdutoVendaService;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoItemService {

    private final PedidoItemRepository repository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoItemMapper mapper;
    private final ProdutoVendaService produtoVendaService;

    @Transactional
    public PedidoItemResponse adicionar(
            Long pedidoId,
            PedidoItemRequest request
    ) {

        Pedido pedido = buscarPedido(pedidoId);

        validarPedidoComercialmenteEditavel(pedido);

        Produto produto = produtoRepository.findById(
                request.getProdutoId()
        ).orElseThrow(() ->
                new RegraNegocioException(
                        "Produto não encontrado."
                )
        );

        if (produto.getSetor() == null) {
            throw new RegraNegocioException(
                    "Produto não possui setor definido."
            );
        }

        ProdutoVenda produtoVenda =
                produtoVendaService.obterProdutoDisponivel(
                        produto.getId(),
                        pedido.getCanalVenda().getId()
                );

        BigDecimal valorUnitario =
                produtoVenda.getPrecoVenda();

        BigDecimal valorTotal =
                valorUnitario.multiply(
                        request.getQuantidade()
                );

        PedidoItem item = PedidoItem.builder()
                .pedido(pedido)
                .produto(produto)
                .quantidade(request.getQuantidade())
                .valorUnitario(valorUnitario)
                .valorTotal(valorTotal)
                .setor(produto.getSetor())
                .ativo(true)
                .build();

        PedidoItem salvo = repository.save(item);

        pedido.getItens().add(salvo);

        recalcularValorPedido(pedido);

        return mapper.toResponse(salvo);
    }

    @Transactional
    public PedidoItemResponse alterarQuantidade(
            Long pedidoId,
            Long itemId,
            BigDecimal quantidade
    ) {

        if (quantidade == null || quantidade.signum() <= 0) {
            throw new RegraNegocioException(
                    "A quantidade deve ser maior que zero."
            );
        }

        Pedido pedido = buscarPedido(pedidoId);

        validarPedidoComercialmenteEditavel(pedido);

        PedidoItem item = repository.findById(itemId)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Item do pedido não encontrado."
                        )
                );

        if (!item.getPedido().getId().equals(pedidoId)) {
            throw new RegraNegocioException(
                    "Item não pertence ao pedido informado."
            );
        }

        if (!Boolean.TRUE.equals(item.getAtivo())) {
            throw new RegraNegocioException(
                    "Item inativo não pode ser alterado."
            );
        }

        BigDecimal valorUnitario = item.getValorUnitario();

        if (valorUnitario == null) {
            throw new RegraNegocioException(
                    "Item não possui valor unitário definido."
            );
        }

        item.setQuantidade(quantidade);
        item.setValorTotal(
                valorUnitario.multiply(quantidade)
        );

        PedidoItem atualizado = repository.save(item);

        recalcularValorPedido(pedido);

        return mapper.toResponse(atualizado);
    }

    @Transactional
    public void remover(
            Long pedidoId,
            Long itemId
    ) {

        Pedido pedido = buscarPedido(pedidoId);

        validarPedidoComercialmenteEditavel(pedido);

        PedidoItem item = repository.findById(itemId)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Item do pedido não encontrado."
                        )
                );

        if (!item.getPedido().getId().equals(pedidoId)) {
            throw new RegraNegocioException(
                    "Item não pertence ao pedido informado."
            );
        }

        if (!Boolean.TRUE.equals(item.getAtivo())) {
            throw new RegraNegocioException(
                    "Item já está inativo."
            );
        }

        item.setAtivo(false);

        repository.save(item);

        recalcularValorPedido(pedido);
    }
    public List<PedidoItemResponse> listar(Long pedidoId) {

        buscarPedido(pedidoId);

        return repository.findByPedidoId(pedidoId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    private Pedido buscarPedido(Long pedidoId) {

        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Pedido não encontrado."
                        )
                );
    }

    private void validarPedidoComercialmenteEditavel(
            Pedido pedido
    ) {

        if (pedido.getStatus() == StatusPedido.FATURADO
                || pedido.getStatus() == StatusPedido.ENTREGUE
                || pedido.getStatus() == StatusPedido.CANCELADO) {

            throw new RegraNegocioException(
                    "Pedido não permite alteração comercial."
            );
        }
    }

    private void recalcularValorPedido(Pedido pedido) {

        BigDecimal totalProdutos =
                repository.findByPedidoId(pedido.getId())
                        .stream()
                        .filter(item ->
                                Boolean.TRUE.equals(item.getAtivo())
                        )
                        .map(PedidoItem::getValorTotal)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        BigDecimal taxaEntrega =
                pedido.getTaxaEntrega() != null
                        ? pedido.getTaxaEntrega()
                        : BigDecimal.ZERO;

        pedido.setValorTotal(
                totalProdutos.add(taxaEntrega)
        );

        pedidoRepository.save(pedido);
    }
}