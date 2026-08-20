package br.com.inova.sigin.pedido.service;

import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import br.com.inova.sigin.canalvenda.repository.CanalVendaRepository;
import br.com.inova.sigin.configuracao.service.ConfiguracaoSistemaService;
import br.com.inova.sigin.financeiro.entity.FormaPagamento;
import br.com.inova.sigin.financeiro.repository.FormaPagamentoRepository;
import br.com.inova.sigin.financeiro.service.FinanceiroPedidoService;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoResponse;
import br.com.inova.sigin.ordemproducao.service.OrdemProducaoService;
import br.com.inova.sigin.pedido.dto.PedidoPagamentoRequest;
import br.com.inova.sigin.pedido.dto.PedidoRequest;
import br.com.inova.sigin.pedido.dto.PedidoResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoEndereco;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import br.com.inova.sigin.pedido.entity.PedidoPagamento;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import br.com.inova.sigin.pedido.enums.TipoRecebimento;
import br.com.inova.sigin.pedido.mapper.PedidoMapper;
import br.com.inova.sigin.pedido.repository.PedidoRepository;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.entity.PessoaEndereco;
import br.com.inova.sigin.pessoa.repository.PessoaEnderecoRepository;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;
import br.com.inova.sigin.produtovenda.service.ProdutoVendaService;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final PessoaRepository pessoaRepository;
    private final PedidoMapper mapper;
    private final ConfiguracaoSistemaService configuracaoSistemaService;
    private final OrdemProducaoService ordemProducaoService;
    private final ProdutoRepository produtoRepository;
    private final CanalVendaRepository canalVendaRepository;
    private final ProdutoVendaService produtoVendaService;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final FinanceiroPedidoService financeiroPedidoService;
    private final PessoaEnderecoRepository pessoaEnderecoRepository;

    @Transactional
    public PedidoResponse criar(PedidoRequest request) {

        Pessoa cliente = pessoaRepository.findById(request.getClienteId())
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Cliente não encontrado."
                        )
                );

        CanalVenda canalVenda = canalVendaRepository
                .findById(request.getCanalVendaId())
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Canal de venda não encontrado."
                        )
                );

        TipoRecebimento tipoRecebimento = request.getTipoRecebimento();

        if (tipoRecebimento == null) {
            throw new RegraNegocioException(
                    "Tipo de recebimento é obrigatório."
            );
        }

        BigDecimal taxaEntrega = validarTaxaEntrega(request);

        if (tipoRecebimento == TipoRecebimento.ENTREGA
                && request.getEnderecoId() == null) {

            throw new RegraNegocioException(
                    "Endereço é obrigatório para entrega."
            );
        }

        Pedido pedido = Pedido.builder()
                .numero(configuracaoSistemaService.gerarProximoNumeroPedido())
                .cliente(cliente)
                .canalVenda(canalVenda)
                .tipoRecebimento(tipoRecebimento)
                .taxaEntrega(taxaEntrega)
                .dataPedido(LocalDateTime.now())
                .status(StatusPedido.ABERTO)
                .ativo(true)
                .observacao(request.getObservacao())
                .build();

        if (request.getEnderecoId() != null) {

            PessoaEndereco endereco =
                    pessoaEnderecoRepository
                            .findByIdAndPessoaId(
                                    request.getEnderecoId(),
                                    cliente.getId()
                            )
                            .orElseThrow(() ->
                                    new RegraNegocioException(
                                            "Endereço não pertence ao cliente."
                                    )
                            );

            PedidoEndereco pedidoEndereco =
                    PedidoEndereco.builder()
                            .pedido(pedido)
                            .pessoaEnderecoId(endereco.getId())
                            .cep(endereco.getCep())
                            .logradouro(endereco.getLogradouro())
                            .numero(endereco.getNumero())
                            .complemento(endereco.getComplemento())
                            .bairro(endereco.getBairro())
                            .cidade(endereco.getCidade())
                            .uf(endereco.getUf())
                            .build();

            pedido.setEndereco(pedidoEndereco);
        }

        List<PedidoItem> itens = request.getItens()
                .stream()
                .map(itemRequest -> {

                    Produto produto = produtoRepository
                            .findById(itemRequest.getProdutoId())
                            .orElseThrow(() ->
                                    new RegraNegocioException(
                                            "Produto não encontrado."
                                    )
                            );

                    ProdutoVenda produtoVenda =
                            produtoVendaService.obterProdutoDisponivel(
                                    produto.getId(),
                                    canalVenda.getId()
                            );

                    BigDecimal valorUnitario =
                            produtoVenda.getPrecoVenda();

                    BigDecimal valorTotal =
                            valorUnitario.multiply(
                                    itemRequest.getQuantidade()
                            );

                    return PedidoItem.builder()
                            .pedido(pedido)
                            .produto(produto)
                            .quantidade(itemRequest.getQuantidade())
                            .valorUnitario(valorUnitario)
                            .valorTotal(valorTotal)
                            .ativo(true)
                            .build();
                })
                .toList();

        pedido.getItens().addAll(itens);

        atualizarValorTotal(pedido);

        adicionarPagamentos(pedido, request.getPagamentos());

        return mapper.toResponse(
                repository.save(pedido)
        );
    }

    private BigDecimal validarTaxaEntrega(PedidoRequest request) {

        TipoRecebimento tipoRecebimento =
                request.getTipoRecebimento();

        BigDecimal taxaInformada =
                request.getTaxaEntrega();

        if (tipoRecebimento == TipoRecebimento.RETIRADA) {

            if (taxaInformada != null
                    && taxaInformada.compareTo(BigDecimal.ZERO) != 0) {

                throw new RegraNegocioException(
                        "Retirada não possui taxa de entrega."
                );
            }

            return BigDecimal.ZERO;
        }

        if (tipoRecebimento == TipoRecebimento.ENTREGA) {

            if (taxaInformada == null) {
                throw new RegraNegocioException(
                        "Taxa de entrega é obrigatória para entrega."
                );
            }

            if (taxaInformada.compareTo(BigDecimal.ZERO) < 0) {
                throw new RegraNegocioException(
                        "Taxa de entrega inválida."
                );
            }

            return taxaInformada;
        }

        throw new RegraNegocioException(
                "Tipo de recebimento inválido."
        );
    }

    public List<PedidoResponse> listar() {

        return repository.listarCompleto()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public PedidoResponse buscarPorId(Long id) {

        Pedido pedido = buscarEntidadePorId(id);

        return mapper.toResponse(pedido);
    }

    private Pedido buscarEntidadePorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Pedido não encontrado."
                        ));
    }

    public PedidoResponse cancelar(Long id) {

        Pedido pedido = buscarEntidadePorId(id);

        if (pedido.getStatus() == StatusPedido.ENTREGUE
                || pedido.getStatus() == StatusPedido.FATURADO) {

            throw new RegraNegocioException(
                    "Não é possível cancelar pedido finalizado."
            );
        }

        pedido.setStatus(StatusPedido.CANCELADO);

        return mapper.toResponse(
                repository.save(pedido)
        );
    }

    public PedidoResponse enviarParaProducao(Long id) {

        Pedido pedido = buscarEntidadePorId(id);

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraNegocioException(
                    "Somente pedidos abertos podem ir para produção."
            );
        }

        pedido.setStatus(StatusPedido.AGUARDANDO_PRODUCAO);

        return mapper.toResponse(
                repository.save(pedido)
        );
    }

    private void atualizarValorTotal(Pedido pedido) {

        BigDecimal totalProdutos = pedido.getItens()
                .stream()
                .map(PedidoItem::getValorTotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        BigDecimal taxaEntrega = pedido.getTaxaEntrega() != null
                ? pedido.getTaxaEntrega()
                : BigDecimal.ZERO;

        pedido.setValorTotal(
                totalProdutos.add(taxaEntrega)
        );
    }

    @Transactional
    public List<OrdemProducaoResponse> gerarOrdemProducao(Long pedidoId) {

        Pedido pedido = repository.findById(pedidoId)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Pedido não encontrado."
                        ));

        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new RegraNegocioException(
                    "Não é possível gerar produção para pedido cancelado."
            );
        }

        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new RegraNegocioException(
                    "Pedido não possui itens."
            );
        }

        List<OrdemProducaoResponse> ordens = pedido.getItens()
                .stream()
                .map(item ->
                        ordemProducaoService.criarAPartirPedidoItem(
                                pedido,
                                item
                        )
                )
                .toList();

        pedido.setStatus(StatusPedido.AGUARDANDO_PRODUCAO);

        repository.save(pedido);

        return ordens;
    }

    @Transactional
    public PedidoResponse faturar(Long id) {

        Pedido pedido = buscarEntidadePorId(id);

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraNegocioException(
                    "Somente pedidos abertos podem ser faturados."
            );
        }

        pedido.setStatus(StatusPedido.FATURADO);

        repository.save(pedido);

        financeiroPedidoService.gerarFinanceiro(pedido);

        return mapper.toResponse(pedido);
    }

    private void adicionarPagamentos(
            Pedido pedido,
            List<PedidoPagamentoRequest> pagamentosRequest
    ) {
        if (pagamentosRequest == null || pagamentosRequest.isEmpty()) {
            throw new RegraNegocioException(
                    "Pedido deve possuir pelo menos um pagamento."
            );
        }

        BigDecimal totalPagamentos = BigDecimal.ZERO;

        for (PedidoPagamentoRequest pagamentoRequest : pagamentosRequest) {

            if (pagamentoRequest.getValor() == null
                    || pagamentoRequest.getValor().compareTo(BigDecimal.ZERO) <= 0) {

                throw new RegraNegocioException(
                        "Valor de pagamento deve ser maior que zero."
                );
            }

            FormaPagamento formaPagamento =
                    formaPagamentoRepository
                            .findById(pagamentoRequest.getFormaPagamentoId())
                            .filter(FormaPagamento::getAtivo)
                            .orElseThrow(() ->
                                    new RegraNegocioException(
                                            "Forma de pagamento não encontrada ou inativa."
                                    )
                            );

            PedidoPagamento pagamento = PedidoPagamento.builder()
                    .pedido(pedido)
                    .formaPagamento(formaPagamento)
                    .valor(pagamentoRequest.getValor())
                    .build();

            pedido.getPagamentos().add(pagamento);

            totalPagamentos = totalPagamentos.add(
                    pagamentoRequest.getValor()
            );
        }

        BigDecimal totalPedido = calcularTotalPedidoSemPagamento(pedido);

        if (totalPagamentos.compareTo(totalPedido) != 0) {
            throw new RegraNegocioException(
                    "A soma dos pagamentos deve ser igual ao total do pedido."
            );
        }
    }
    private BigDecimal calcularTotalPedidoSemPagamento(Pedido pedido) {

        BigDecimal totalProdutos = pedido.getItens()
                .stream()
                .map(PedidoItem::getValorTotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        BigDecimal taxaEntrega = pedido.getTaxaEntrega() != null
                ? pedido.getTaxaEntrega()
                : BigDecimal.ZERO;

        return totalProdutos.add(taxaEntrega);
    }
}