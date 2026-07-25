package br.com.inova.sigin.api.delivery.balcao.service;

import br.com.inova.sigin.api.delivery.balcao.dto.BalcaoPedidoResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import br.com.inova.sigin.pedido.repository.PedidoRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BalcaoService {

    private final PedidoRepository repository;

    @Transactional(readOnly = true)
    public List<BalcaoPedidoResponse> listar() {

        return repository.findByStatus(StatusPedido.ABERTO).stream().map(p -> BalcaoPedidoResponse.builder().id(p.getId()).numero(p.getNumero()).cliente(p.getCliente().getNome()).status(p.getStatus().name()).valorTotal(p.getValorTotal()).dataPedido(p.getDataPedido()).build()).toList();
    }

    @Transactional
    public void aceitar(Long id) {

        Pedido pedido = repository.findById(id).orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"));

        pedido.setStatus(StatusPedido.RECEBIDO);

        repository.save(pedido);
    }

    @Transactional
    public void cancelar(Long id) {

        Pedido pedido = repository.findById(id).orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"));

        pedido.setStatus(StatusPedido.CANCELADO);

        repository.save(pedido);
    }

    @Transactional
    public void enviarParaCozinha(Long id) {

        Pedido pedido = repository.findById(id).orElseThrow(() -> new RegraNegocioException("Pedido não encontrado"));

        pedido.setStatus(StatusPedido.EM_PREPARO);

        repository.save(pedido);
    }

}