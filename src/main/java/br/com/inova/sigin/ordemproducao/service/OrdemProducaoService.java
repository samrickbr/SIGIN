package br.com.inova.sigin.ordemproducao.service;

import br.com.inova.sigin.configuracao.service.ConfiguracaoSistemaService;
import br.com.inova.sigin.local.entity.Local;
import br.com.inova.sigin.local.repository.LocalRepository;
import br.com.inova.sigin.opmaterial.service.OpMaterialService;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoRequest;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoResponse;
import br.com.inova.sigin.ordemproducao.dto.OrdemProducaoUpdateRequest;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.ordemproducao.enums.StatusOrdemProducao;
import br.com.inova.sigin.ordemproducao.mapper.OrdemProducaoMapper;
import br.com.inova.sigin.ordemproducao.repository.OrdemProducaoRepository;
import br.com.inova.sigin.pedido.entity.Pedido;
import br.com.inova.sigin.pedido.entity.PedidoItem;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.produto.entity.Produto;
import br.com.inova.sigin.produto.repository.ProdutoRepository;
import br.com.inova.sigin.reservaestoque.service.ReservaEstoqueService;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdemProducaoService {

    private final OrdemProducaoRepository repository;
    private final ProdutoRepository produtoRepository;
    private final LocalRepository localRepository;
    private final PessoaRepository pessoaRepository;
    private final OrdemProducaoMapper mapper;
    private final ReservaEstoqueService reservaEstoqueService;
    private final ConfiguracaoSistemaService configuracaoSistemaService;
    private final OpMaterialService opMaterialService;


    private Produto buscarProduto(Long id) {

        return produtoRepository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Produto não encontrado"));
    }

    private Local buscarLocal(Long id) {

        return localRepository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Local não encontrado"));
    }

    private Pessoa buscarResponsavel(Long id) {

        if (id == null) {
            return null;
        }

        return pessoaRepository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException("Responsável não encontrado"));
    }

    @Transactional
    public OrdemProducaoResponse criar(OrdemProducaoRequest request) {
        Produto produto = buscarProduto(request.getProdutoId());

        Local local = buscarLocal(request.getLocalDestinoId());

        Pessoa responsavel = buscarResponsavel(request.getResponsavelId());

        OrdemProducao op =
                montarOrdemProducao(
                        produto,
                        local,
                        responsavel,
                        request
                );

        return salvar(op);
    }

    public List<OrdemProducaoResponse> listar() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    public OrdemProducaoResponse buscarPorId(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        return mapper.toResponse(op);
    }


    public OrdemProducaoResponse atualizar(
            Long id,
            OrdemProducaoUpdateRequest request) {

        OrdemProducao op = buscarEntidadePorId(id);

        if (request.getQuantidadePlanejada() != null) {
            op.setQuantidadePlanejada(request.getQuantidadePlanejada());
        }

        if (request.getQuantidadeProduzida() != null) {
            op.setQuantidadeProduzida(request.getQuantidadeProduzida());
        }

        if (request.getOrigem() != null) {
            op.setOrigem(request.getOrigem().toUpperCase());
        }

        if (request.getObservacao() != null) {
            op.setObservacao(request.getObservacao());
        }

        if (request.getAtivo() != null) {
            op.setAtivo(request.getAtivo());
        }

        return salvar(op);
    }


    public void excluir(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        op.setAtivo(false);

        repository.save(op);
    }

    @Transactional
    public OrdemProducaoResponse reservar(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        validarReserva(op);

        reservaEstoqueService.reservarMateriais(op);

        op.setStatus(StatusOrdemProducao.RESERVADA);

        return salvar(op);
    }

    @Transactional
    public OrdemProducaoResponse iniciar(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        validarInicio(op);

        op.setStatus(StatusOrdemProducao.EM_PRODUCAO);

        return salvar(op);
    }

    @Transactional
    public OrdemProducaoResponse concluir(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        validarConclusao(op);

        op.setStatus(StatusOrdemProducao.CONCLUIDA);

        return salvar(op);
    }

    @Transactional
    public OrdemProducaoResponse cancelar(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        validarCancelamento(op);

        op.setStatus(StatusOrdemProducao.CANCELADA);

        return salvar(op);
    }

    @Transactional
    public OrdemProducaoResponse falhar(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        validarFalha(op);

        op.setStatus(StatusOrdemProducao.FALHA_PRODUCAO);

        return salvar(op);
    }

    @Transactional
    public OrdemProducaoResponse reabrir(Long id) {

        OrdemProducao op = buscarEntidadePorId(id);

        validarReabertura(op);

        op.setStatus(StatusOrdemProducao.RESERVADA);

        return salvar(op);
    }

    private OrdemProducao buscarEntidadePorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Ordem de produção não encontrada"));
    }

    @Transactional
    public OrdemProducaoResponse criarAPartirPedidoItem(
            Pedido pedido,
            PedidoItem item) {
        Produto produto = item.getProduto();
        Local local = buscarLocal(
                configuracaoSistemaService.getLocalProducaoPadraoId()
        );
        OrdemProducao salva = salvarEntidade(
                montarOrdemPedido(
                        pedido,
                        item,
                        local
                )
        );
        opMaterialService.gerarMateriaisDaOP(salva);
        return mapper.toResponse(salva);
    }

    private OrdemProducao montarOrdemProducao(
            Produto produto,
            Local local,
            Pessoa responsavel,
            OrdemProducaoRequest request) {
        return OrdemProducao.builder()
                .numero(configuracaoSistemaService.gerarProximoNumeroOp())
                .produto(produto)
                .quantidadePlanejada(request.getQuantidadePlanejada())
                .localDestino(local)
                .responsavel(responsavel)
                .status(StatusOrdemProducao.ABERTA)
                .origem(request.getOrigem().toUpperCase())
                .observacao(request.getObservacao())
                .quantidadeProduzida(BigDecimal.ZERO)
                .ativo(true)
                .dataAbertura(LocalDateTime.now())
                .build();
    }

    private OrdemProducao montarOrdemPedido(
            Pedido pedido,
            PedidoItem item,
            Local local) {

        return OrdemProducao.builder()
                .numero(configuracaoSistemaService.gerarProximoNumeroOp())
                .produto(item.getProduto())
                .quantidadePlanejada(item.getQuantidade())
                .localDestino(local)
                .responsavel(null)
                .pedido(pedido)
                .status(StatusOrdemProducao.ABERTA)
                .origem("PEDIDO")
                .observacao(
                        "Gerada automaticamente pelo pedido "
                                + pedido.getNumero()
                )
                .quantidadeProduzida(BigDecimal.ZERO)
                .ativo(true)
                .dataAbertura(LocalDateTime.now())
                .build();
    }

    private void validarReserva(OrdemProducao op) {
        if (op.getStatus() != StatusOrdemProducao.ABERTA) {
            throw new RegraNegocioException(
                    "Apenas OPs abertas podem ser reservadas.");
        }
    }

    private void validarInicio(OrdemProducao op) {
        if (op.getStatus() != StatusOrdemProducao.RESERVADA) {
            throw new RegraNegocioException(
                    "Apenas OPs reservadas podem iniciar a produção.");
        }
    }

    private void validarConclusao(OrdemProducao op) {
        if (op.getStatus() != StatusOrdemProducao.EM_PRODUCAO) {
            throw new RegraNegocioException(
                    "Apenas OPs em produção podem ser concluídas.");
        }
    }
    private void validarCancelamento(OrdemProducao op) {
        if (op.getStatus() == StatusOrdemProducao.CONCLUIDA) {
            throw new RegraNegocioException(
                    "Não é possível cancelar uma OP concluída.");
        }
    }

    private void validarFalha(OrdemProducao op) {
        if (op.getStatus() != StatusOrdemProducao.EM_PRODUCAO) {
            throw new RegraNegocioException(
                    "Apenas OPs em produção podem falhar.");
        }
    }

    private void validarReabertura(OrdemProducao op) {
        if (op.getStatus() != StatusOrdemProducao.FALHA_PRODUCAO) {
            throw new RegraNegocioException(
                    "Apenas OPs com falha podem ser reabertas.");
        }
    }
    private OrdemProducaoResponse salvar(OrdemProducao op) {
        return mapper.toResponse(
                salvarEntidade(op)
        );
    }
    private OrdemProducao salvarEntidade(OrdemProducao op) {
        return repository.save(op);
    }
}