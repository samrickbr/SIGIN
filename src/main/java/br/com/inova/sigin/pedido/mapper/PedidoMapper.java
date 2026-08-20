package br.com.inova.sigin.pedido.mapper;

import br.com.inova.sigin.pedido.dto.PedidoEnderecoResponse;
import br.com.inova.sigin.pedido.dto.PedidoResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoEndereco;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoResponse toResponse(Pedido pedido) {

        return PedidoResponse.builder()
                .id(pedido.getId())
                .numero(pedido.getNumero())
                .clienteId(pedido.getCliente().getId())
                .cliente(pedido.getCliente().getNome())
                .endereco(
                        converterEndereco(pedido.getEndereco())
                )
                .tipoRecebimento(pedido.getTipoRecebimento().name())
                .canalVendaId(pedido.getCanalVenda().getId())
                .canalVenda(pedido.getCanalVenda().getNome())
                .dataPedido(pedido.getDataPedido())
                .valorTotal(pedido.getValorTotal())
                .status(pedido.getStatus().name())
                .formaPagamentoId(pedido.getFormaPagamento().getId())
                .formaPagamento(pedido.getFormaPagamento().getDescricao())
                .ativo(pedido.getAtivo())
                .observacao(pedido.getObservacao())
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
}