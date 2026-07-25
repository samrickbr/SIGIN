package br.com.inova.sigin.api.delivery.carrinho.service;

import br.com.inova.sigin.api.delivery.carrinho.dto.AdicionarItemRequest;
import br.com.inova.sigin.api.delivery.carrinho.dto.CarrinhoItemResponse;
import br.com.inova.sigin.api.delivery.carrinho.dto.CarrinhoResponse;
import br.com.inova.sigin.api.delivery.carrinho.entity.Carrinho;
import br.com.inova.sigin.api.delivery.carrinho.entity.CarrinhoItem;
import br.com.inova.sigin.api.delivery.carrinho.entity.CarrinhoStatus;
import br.com.inova.sigin.api.delivery.carrinho.repository.CarrinhoItemRepository;
import br.com.inova.sigin.api.delivery.carrinho.repository.CarrinhoRepository;
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

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final CarrinhoItemRepository itemRepository;
    private final ProdutoVendaRepository produtoVendaRepository;
    private final PessoaRepository pessoaRepository;
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
        BigDecimal valorTotal =
                produtoVenda.getPrecoVenda()
                        .multiply(request.getQuantidade());

        CarrinhoItem item = CarrinhoItem.builder()
                .carrinho(carrinho)
                .produtoVenda(produtoVenda)
                .quantidade(request.getQuantidade())
                .valorUnitario(produtoVenda.getPrecoVenda())
                .valorTotal(valorTotal)
                .build();

        itemRepository.save(item);

        recalcularTotal(carrinho);

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
}