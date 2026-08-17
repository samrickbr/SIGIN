package br.com.inova.sigin.integracao.core;

import br.com.inova.sigin.pedido.dto.PedidoRequest;
import br.com.inova.sigin.pedido.dto.PedidoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class CoreClient {

    private final RestClient coreRestClient;

    public PedidoResponse criarPedido(PedidoRequest request) {

        return coreRestClient
                .post()
                .uri("/pedidos")
                .body(request)
                .retrieve()
                .body(PedidoResponse.class);
    }
}