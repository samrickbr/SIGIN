package br.com.inova.sigin.fluxo.service;

import br.com.inova.sigin.apontamentoproducao.dto.ApontamentoProducaoRequest;
import br.com.inova.sigin.apontamentoproducao.service.ApontamentoProducaoService;
import br.com.inova.sigin.dev.dto.FluxoPedidoResponse;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoResponse;
import br.com.inova.sigin.ordemproducao.service.OrdemProducaoService;
import br.com.inova.sigin.pedido.dto.PedidoItemRequest;
import br.com.inova.sigin.pedido.dto.PedidoRequest;
import br.com.inova.sigin.pedido.dto.PedidoResponse;
import br.com.inova.sigin.pedido.service.PedidoItemService;
import br.com.inova.sigin.pedido.service.PedidoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FluxoPedidoService {

    private final PedidoService pedidoService;
    private final PedidoItemService pedidoItemService;
    private final OrdemProducaoService ordemProducaoService;
    private final ApontamentoProducaoService apontamentoProducaoService;

    @Transactional
    public FluxoPedidoResponse executar() {

        PedidoRequest pedidoRequest = new PedidoRequest();
        pedidoRequest.setClienteId(1L);
        pedidoRequest.setObservacao("Pedido criado pelo fluxo DEV");

        PedidoResponse pedido = pedidoService.criar(pedidoRequest);

        PedidoItemRequest itemRequest = new PedidoItemRequest();
        itemRequest.setProdutoId(3L);
        itemRequest.setQuantidade(new BigDecimal("10"));

        pedidoItemService.adicionar(
                pedido.getId(),
                itemRequest
        );
        System.out.println("PEDIDO ID: " + pedido.getId());

        List<OrdemProducaoResponse> ordens =
                pedidoService.gerarOrdemProducao(
                        pedido.getId()
                );

        return FluxoPedidoResponse.builder()
                .pedidoId(pedido.getId())
                .ordensProducao(
                        ordens.stream()
                                .map(OrdemProducaoResponse::getId)
                                .toList()
                )
                .status("OK")
                .build();
    }
    @Transactional
    public void produzir(Long ordemProducaoId) {

        ApontamentoProducaoRequest request =
                new ApontamentoProducaoRequest();

        request.setOrdemProducaoId(ordemProducaoId);
        request.setQuantidadeProduzida(
                new BigDecimal("10")
        );
        request.setQuantidadePerda(
                BigDecimal.ZERO
        );
        request.setObservacao(
                "Produção automática DEV"
        );

        apontamentoProducaoService.criar(request);
    }
}