package br.com.inova.sigin.financeiro.service;

import br.com.inova.sigin.financeiro.entity.CaixaMovimento;
import br.com.inova.sigin.financeiro.entity.ContaReceber;
import br.com.inova.sigin.financeiro.entity.FormaPagamento;
import br.com.inova.sigin.financeiro.enums.OrigemMovimentoCaixa;
import br.com.inova.sigin.financeiro.enums.StatusContaReceber;
import br.com.inova.sigin.financeiro.enums.TipoMovimentoCaixa;
import br.com.inova.sigin.financeiro.repository.CaixaMovimentoRepository;
import br.com.inova.sigin.financeiro.repository.ContaReceberRepository;
import br.com.inova.sigin.pedido.entity.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FinanceiroPedidoService {

    private final ContaReceberRepository contaReceberRepository;
    private final CaixaMovimentoRepository caixaMovimentoRepository;

    @Transactional
    public void gerarFinanceiro(Pedido pedido) {

        FormaPagamento forma = pedido.getFormaPagamento();

        boolean baixaAutomatica =
                Boolean.TRUE.equals(forma.getBaixaAutomatica());

        ContaReceber conta = ContaReceber.builder()
                .pessoa(pedido.getCliente())
                .pedido(pedido)
                .formaPagamento(forma)
                .valor(pedido.getValorTotal())
                .dataVencimento(LocalDate.now())
                .status(
                        baixaAutomatica
                                ? StatusContaReceber.PAGA
                                : StatusContaReceber.ABERTA
                )
                .build();

        contaReceberRepository.save(conta);

        if (baixaAutomatica) {

            CaixaMovimento movimento =
                    CaixaMovimento.builder()
                            .tipo(TipoMovimentoCaixa.ENTRADA)
                            .origem(OrigemMovimentoCaixa.PEDIDO)
                            .valor(pedido.getValorTotal())
                            .referenciaId(pedido.getId())
                            .observacao("Pedido " + pedido.getNumero())
                            .build();

            caixaMovimentoRepository.save(movimento);
        }
    }
}