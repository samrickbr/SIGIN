package br.com.inova.sigin.pedido.mapper;

import br.com.inova.sigin.pedido.dto.PedidoEnderecoResponse;
import br.com.inova.sigin.pedido.dto.PedidoItemResponse;
import br.com.inova.sigin.pedido.dto.PedidoPagamentoResponse;
import br.com.inova.sigin.pedido.dto.PedidoResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoEndereco;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import br.com.inova.sigin.pedido.entity.PedidoPagamento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PedidoMapper {

    public PedidoResponse toResponse(Pedido pedido) {

        BigDecimal valorProdutos = pedido.getItens()
                .stream()
                .map(PedidoItem::getValorTotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        return PedidoResponse.builder()
                .id(pedido.getId())
                .numero(pedido.getNumero())
                .clienteId(pedido.getCliente().getId())
                .cliente(pedido.getCliente().getNome())
                .endereco(
                        converterEndereco(pedido.getEndereco())
                )
                .tipoRecebimento(
                        pedido.getTipoRecebimento().name()
                )
                .canalVendaId(pedido.getCanalVenda().getId())
                .canalVenda(pedido.getCanalVenda().getNome())
                .dataPedido(pedido.getDataPedido())
                .valorProdutos(valorProdutos)
                .taxaEntrega(pedido.getTaxaEntrega())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus().name())
                .pagamentos(
                        pedido.getPagamentos()
                                .stream()
                                .map(this::converterPagamento)
                                .toList()
                )
                .itens(
                        pedido.getItens()
                                .stream()
                                .map(this::converterItem)
                                .toList()
                )
                .ativo(pedido.getAtivo())
                .observacao(pedido.getObservacao())
                .build();
    }

    private PedidoItemResponse converterItem(
            PedidoItem item
    ) {

        return PedidoItemResponse.builder()
                .id(item.getId())
                .produtoId(item.getProduto().getId())
                .produto(item.getProduto().getNome())
                .quantidade(item.getQuantidade())
                .valorUnitario(item.getValorUnitario())
                .valorTotal(item.getValorTotal())
                .setor(item.getSetor())
                .ativo(item.getAtivo())
                .build();
    }

    private PedidoEnderecoResponse converterEndereco(
            PedidoEndereco endereco
    ) {

        if (endereco == null) {
            return null;
        }

        return PedidoEnderecoResponse.builder()
                .id(endereco.getId())
                .pessoaEnderecoId(endereco.getPessoaEnderecoId())
                .cep(endereco.getCep())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .uf(endereco.getUf())
                .build();
    }

    private PedidoPagamentoResponse converterPagamento(
            PedidoPagamento pagamento
    ) {

        return PedidoPagamentoResponse.builder()
                .id(pagamento.getId())
                .formaPagamentoId(
                        pagamento.getFormaPagamento().getId()
                )
                .formaPagamento(
                        pagamento.getFormaPagamento().getDescricao()
                )
                .valor(pagamento.getValor())
                .build();
    }
}