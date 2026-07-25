package br.com.inova.sigin.api.delivery.carrinho.service;

import br.com.inova.sigin.api.delivery.carrinho.dto.AdicionarItemRequest;
import br.com.inova.sigin.api.delivery.carrinho.dto.CarrinhoItemResponse;
import br.com.inova.sigin.api.delivery.carrinho.dto.CarrinhoResponse;
import br.com.inova.sigin.api.delivery.carrinho.entity.Carrinho;
import br.com.inova.sigin.api.delivery.carrinho.entity.CarrinhoItem;
import br.com.inova.sigin.api.delivery.carrinho.entity.CarrinhoStatus;
import br.com.inova.sigin.api.delivery.carrinho.repository.CarrinhoItemRepository;
import br.com.inova.sigin.api.delivery.carrinho.repository.CarrinhoRepository;
import br.com.inova.sigin.pedido.dto.PedidoResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import br.com.inova.sigin.pedido.repository.PedidoItemRepository;
import br.com.inova.sigin.pedido.repository.PedidoRepository;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;
import br.com.inova.sigin.produtovenda.repository.ProdutoVendaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ProdutoVendaRepository produtoVendaRepository;
    private final CarrinhoItemRepository carrinhoItemRepository;
    private final PessoaRepository pessoaRepository;
    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    @Transactional
    public CarrinhoResponse criar(
            Long clienteId
    ) {

        Pessoa cliente = pessoaRepository.findById(clienteId)
                .orElseThrow(() ->
                        new RegraNegocioException("Cliente não encontrado")
                );
        Carrinho carrinho = Carrinho.builder()
                .cliente(cliente)
                .status(CarrinhoStatus.ABERTO)
                .valorTotal(BigDecimal.ZERO)
                .dataCriacao(LocalDateTime.now())
                .build();
        return converter(
                carrinhoRepository.save(carrinho)
        );
    }

    @Transactional
    public CarrinhoResponse adicionarItem(
            Long carrinhoId,
            AdicionarItemRequest request
    ) {

        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() ->
                        new RegraNegocioException("Carrinho não encontrado")
                );

        ProdutoVenda produtoVenda =
                produtoVendaRepository.findById(request.getProdutoVendaId())
                        .orElseThrow(() ->
                                new RegraNegocioException("Produto não encontrado")
                        );
        BigDecimal valorUnitario = produtoVenda.getPrecoVenda();
        BigDecimal valorTotal =
                produtoVenda.getPrecoVenda()
                        .multiply(request.getQuantidade());

        CarrinhoItem item = CarrinhoItem.builder()
                .carrinho(carrinho)
                .produtoVenda(produtoVenda)
                .quantidade(request.getQuantidade())
                .valorUnitario(valorUnitario)
                .valorTotal(valorTotal)
                .build();

        carrinhoItemRepository.save(item);

        carrinho.setValorTotal(
                carrinho.getValorTotal().add(valorTotal)
        );

        return converter(carrinho);
    }

    @Transactional(readOnly = true)
    public CarrinhoResponse buscar(Long id) {

        Carrinho carrinho = carrinhoRepository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Carrinho não encontrado")
                );
        return converter(carrinho);
    }

    private void recalcularTotal(Carrinho carrinho) {
        BigDecimal total = carrinho.getItens()
                .stream()
                .map(CarrinhoItem::getValorTotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        carrinho.setValorTotal(total);

        carrinhoRepository.save(carrinho);
    }


    private CarrinhoResponse converter(Carrinho carrinho) {

        List<CarrinhoItemResponse> itens =
                carrinho.getItens()
                        .stream()
                        .map(item ->
                                CarrinhoItemResponse.builder()
                                        .id(item.getId())
                                        .produtoId(
                                                item.getProdutoVenda()
                                                        .getProduto()
                                                        .getId()
                                        )
                                        .produto(
                                                item.getProdutoVenda()
                                                        .getProduto()
                                                        .getNome()
                                        )
                                        .quantidade(item.getQuantidade())
                                        .valorUnitario(item.getValorUnitario())
                                        .valorTotal(item.getValorTotal())
                                        .build()
                        )
                        .toList();


        return CarrinhoResponse.builder()
                .id(carrinho.getId())
                .status(carrinho.getStatus())
                .valorTotal(carrinho.getValorTotal())
                .itens(itens)
                .build();
    }
    @Transactional
    public CarrinhoResponse atualizarQuantidade(
            Long carrinhoId,
            Long itemId,
            BigDecimal quantidade
    ) {

        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() ->
                        new RegraNegocioException("Carrinho não encontrado"));

        CarrinhoItem item = carrinhoItemRepository.findById(itemId)
                .orElseThrow(() ->
                        new RegraNegocioException("Item não encontrado"));

        if (!item.getCarrinho().getId().equals(carrinhoId)) {
            throw new RegraNegocioException("Item não pertence ao carrinho");
        }

        item.setQuantidade(quantidade);

        item.setValorTotal(
                item.getValorUnitario().multiply(quantidade)
        );

        carrinhoItemRepository.save(item);

        recalcularTotal(carrinho);

        carrinhoRepository.save(carrinho);

        return converter(carrinho);
    }

    @Transactional
    public CarrinhoResponse removerItem(
            Long carrinhoId,
            Long itemId
    ) {

        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() ->
                        new RegraNegocioException("Carrinho não encontrado"));

        CarrinhoItem item = carrinhoItemRepository.findById(itemId)
                .orElseThrow(() ->
                        new RegraNegocioException("Item não encontrado"));

        if (!item.getCarrinho().getId().equals(carrinhoId)) {
            throw new RegraNegocioException("Item não pertence ao carrinho");
        }

        carrinhoItemRepository.delete(item);

        carrinho.getItens().removeIf(i -> i.getId().equals(itemId));

        recalcularTotal(carrinho);

        carrinhoRepository.save(carrinho);

        return converter(carrinho);
    }
    @Transactional
    public CarrinhoResponse limparCarrinho(Long carrinhoId) {

        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() ->
                        new RegraNegocioException("Carrinho não encontrado"));

        carrinhoItemRepository.deleteAll(carrinho.getItens());

        carrinho.getItens().clear();

        carrinho.setValorTotal(BigDecimal.ZERO);

        carrinhoRepository.save(carrinho);

        return converter(carrinho);
    }

    @Transactional
    public PedidoResponse finalizar(Long carrinhoId) {

        Carrinho carrinho = carrinhoRepository.findById(carrinhoId)
                .orElseThrow(() ->
                        new RegraNegocioException("Carrinho não encontrado"));

        if (carrinho.getItens().isEmpty()) {
            throw new RegraNegocioException("Carrinho vazio");
        }

        Pedido pedido = Pedido.builder()
                .numero(UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .cliente(carrinho.getCliente())
                .dataPedido(LocalDateTime.now())
                .status(StatusPedido.ABERTO)
                .valorTotal(carrinho.getValorTotal())
                .ativo(true)
                .build();

        pedidoRepository.save(pedido);

        for (CarrinhoItem item : carrinho.getItens()) {

            PedidoItem pedidoItem = PedidoItem.builder()
                    .pedido(pedido)
                    .produto(item.getProdutoVenda().getProduto())
                    .quantidade(item.getQuantidade())
                    .valorUnitario(item.getValorUnitario())
                    .valorTotal(item.getValorTotal())
                    .ativo(true)
                    .build();

            pedidoItemRepository.save(pedidoItem);
        }

        carrinhoItemRepository.deleteAll(carrinho.getItens());

        carrinho.getItens().clear();
        carrinho.setValorTotal(BigDecimal.ZERO);
        carrinho.setStatus(CarrinhoStatus.FINALIZADO);

        carrinhoRepository.save(carrinho);

        return PedidoResponse.builder()
                .id(pedido.getId())
                .numero(pedido.getNumero())
                .clienteId(pedido.getCliente().getId())
                .cliente(pedido.getCliente().getNome())
                .dataPedido(pedido.getDataPedido())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus().name())
                .ativo(pedido.getAtivo())
                .observacao(pedido.getObservacao())
                .build();
    }
}