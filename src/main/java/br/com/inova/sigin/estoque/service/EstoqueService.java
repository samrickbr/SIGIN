package br.com.inova.sigin.estoque.service;

import br.com.inova.sigin.estoque.dto.EstoqueResponse;
import br.com.inova.sigin.movimentacaoestoque.entity.MovimentacaoEstoque;
import br.com.inova.sigin.movimentacaoestoque.repository.MovimentacaoEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final MovimentacaoEstoqueRepository movimentacaoRepository;


    public List<EstoqueResponse> listarSaldo() {

        List<MovimentacaoEstoque> movimentacoes =
                movimentacaoRepository.findAll();


        Map<String, BigDecimal> saldo = movimentacoes.stream()
                .collect(Collectors.groupingBy(
                        this::chaveProdutoMaterial,
                        Collectors.mapping(
                                this::quantidadeMovimentacao,
                                Collectors.reducing(
                                        BigDecimal.ZERO,
                                        BigDecimal::add
                                )
                        )
                ));


        return saldo.entrySet()
                .stream()
                .map(entry ->
                        EstoqueResponse.builder()
                                .nome(entry.getKey())
                                .saldo(entry.getValue())
                                .build()
                )
                .toList();
    }


    private String chaveProdutoMaterial(
            MovimentacaoEstoque movimentacao) {

        if (movimentacao.getProduto() != null) {
            return movimentacao.getProduto().getNome();
        }

        return movimentacao.getMaterial().getNome();
    }

    private BigDecimal quantidadeMovimentacao(
            MovimentacaoEstoque movimentacao) {
        if ("SAIDA".equalsIgnoreCase(
                movimentacao.getMovimento())) {
            return movimentacao.getQuantidade()
                    .negate();
        }
        return movimentacao.getQuantidade();
    }
}