package br.com.inova.sigin.pedido.service;

import br.com.inova.sigin.configuracao.service.ConfiguracaoSistemaService;
import br.com.inova.sigin.pedido.dto.PedidoRequest;
import br.com.inova.sigin.pedido.dto.PedidoResponse;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import br.com.inova.sigin.pedido.mapper.PedidoMapper;
import br.com.inova.sigin.pedido.repository.PedidoRepository;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoResponse;
import br.com.inova.sigin.ordemproducao.service.OrdemProducaoService;

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

    public PedidoResponse criar(PedidoRequest request) {

        Pessoa cliente = pessoaRepository.findById(request.getClienteId())
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Cliente não encontrado."
                        ));

        Pedido pedido = Pedido.builder()
                .numero(configuracaoSistemaService.gerarProximoNumeroPedido())
                .cliente(cliente)
                .dataPedido(LocalDateTime.now())
                .valorTotal(
                        request.getValorTotal() != null
                                ? request.getValorTotal()
                                : BigDecimal.ZERO
                )
                .status(StatusPedido.ABERTO)
                .ativo(true)
                .observacao(request.getObservacao())
                .build();


        return mapper.toResponse(repository.save(pedido));
    }


    public List<PedidoResponse> listar() {

        return repository.findAll()
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

        if (pedido.getStatus() == StatusPedido.ENTREGUE ||
                pedido.getStatus() == StatusPedido.FATURADO) {

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

        BigDecimal total = pedido.getItens()
                .stream()
                .map(PedidoItem::getValorTotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        pedido.setValorTotal(total);
    }
    @Transactional
    public List<OrdemProducaoResponse> gerarOrdemProducao(Long pedidoId) {

        Pedido pedido = repository.findById(pedidoId)
                .orElseThrow(() ->
                        new RegraNegocioException("Pedido não encontrado."));

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
}