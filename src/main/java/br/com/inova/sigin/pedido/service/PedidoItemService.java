package br.com.inova.sigin.pedido.service;

import br.com.inova.sigin.pedido.dto.PedidoItemRequest;
import br.com.inova.sigin.pedido.dto.PedidoItemResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import br.com.inova.sigin.pedido.mapper.PedidoItemMapper;
import br.com.inova.sigin.pedido.repository.PedidoItemRepository;
import br.com.inova.sigin.pedido.repository.PedidoRepository;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
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


    public PedidoItemResponse adicionar(
            Long pedidoId,
            PedidoItemRequest request) {


        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Pedido não encontrado."
                        ));


        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Produto não encontrado."
                        ));


        BigDecimal valorUnitario =
                request.getValorUnitario() != null
                        ? request.getValorUnitario()
                        : BigDecimal.ZERO;


        PedidoItem item = PedidoItem.builder()
                .pedido(pedido)
                .produto(produto)
                .quantidade(request.getQuantidade())
                .valorUnitario(valorUnitario)
                .valorTotal(
                        valorUnitario.multiply(
                                request.getQuantidade()
                        )
                )
                .ativo(true)
                .build();


        PedidoItem salvo = repository.save(item);

        atualizarValorPedido(pedido);

        return mapper.toResponse(salvo);
    }

    public List<PedidoItemResponse> listar(Long pedidoId) {

        return repository.findByPedidoId(pedidoId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    private void atualizarValorPedido(Pedido pedido) {

        BigDecimal total = repository.findByPedidoId(pedido.getId())
                .stream()
                .map(PedidoItem::getValorTotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );
        pedido.setValorTotal(total);
        pedidoRepository.save(pedido);
    }
}