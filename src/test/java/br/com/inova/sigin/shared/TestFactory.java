package br.com.inova.sigin.shared;

import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import br.com.inova.sigin.pedido.repository.PedidoItemRepository;
import br.com.inova.sigin.pedido.repository.PedidoRepository;
import br.com.inova.sigin.pedido.service.PedidoService;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestFactory {

    private final PessoaRepository pessoaRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final PedidoService pedidoService;
    private final OrdemProducaoRepository ordemProducaoRepository;

    public Pedido criarPedidoSemItens() {

        Pessoa cliente = criarCliente();

        Pedido pedido = Pedido.builder()
                .numero("TESTE-" + System.nanoTime())
                .cliente(cliente)
                .dataPedido(LocalDateTime.now())
                .status(StatusPedido.ABERTO)
                .valorTotal(BigDecimal.ZERO)
                .ativo(true)
                .build();

        return pedidoRepository.save(pedido);
    }
    public Pessoa criarCliente() {

        return pessoaRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Cliente de teste não encontrado."
                        ));
    }
    public Pedido criarPedidoComItens() {

        return criarPedidoComItens(BigDecimal.TEN);
    }
    public Pedido criarPedidoComItens(BigDecimal quantidade) {

        Pedido pedido = criarPedidoSemItens();

        Produto produto = produtoRepository.findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Produto de teste não encontrado."
                        ));

        PedidoItem item = PedidoItem.builder()
                .pedido(pedido)
                .produto(produto)
                .quantidade(quantidade)
                .valorUnitario(BigDecimal.TEN)
                .valorTotal(
                        quantidade.multiply(BigDecimal.TEN)
                )
                .ativo(true)
                .build();

        pedidoItemRepository.save(item);

        pedido.getItens().add(item);

        return pedido;
    }
    public OrdemProducao criarOrdemProducao() {

       return criarOrdemProducao(BigDecimal.TEN);
    }
    public OrdemProducao criarOrdemProducao(
            BigDecimal quantidadePlanejada) {

        Pedido pedido =
                criarPedidoComItens(quantidadePlanejada);

        return pedidoService
                .gerarOrdemProducao(pedido.getId())
                .stream()
                .findFirst()
                .map(response ->
                        ordemProducaoRepository.findById(response.getId())
                                .orElseThrow())
                .orElseThrow();
    }
    public Pedido criarPedidoComProdutoSemFicha() {

        Pedido pedido = criarPedidoSemItens();

        Produto produto = Produto.builder()
                .codigo("TESTE-" + System.nanoTime())
                .nome("Produto sem ficha técnica")
                .descricao("Produto criado para testes")
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .build();

        produto = produtoRepository.save(produto);

        PedidoItem item = PedidoItem.builder()
                .pedido(pedido)
                .produto(produto)
                .quantidade(BigDecimal.ONE)
                .valorUnitario(BigDecimal.TEN)
                .valorTotal(BigDecimal.TEN)
                .ativo(true)
                .build();

        pedidoItemRepository.save(item);

        pedido.getItens().add(item);

        return pedido;
    }
}