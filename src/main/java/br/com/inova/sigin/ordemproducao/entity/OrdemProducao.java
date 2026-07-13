package br.com.inova.sigin.ordemproducao.entity;

import br.com.inova.sigin.local.entity.Local;
import br.com.inova.sigin.ordemproducao.enums.StatusOrdemProducao;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.produto.entity.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordens_producao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdemProducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(name = "quantidade_planejada", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadePlanejada;

    @Column(name = "quantidade_produzida", nullable = false, precision = 12, scale = 3)
    @Builder.Default
    private BigDecimal quantidadeProduzida = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_destino_id", nullable = false)
    private Local localDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private Pessoa responsavel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusOrdemProducao status;

    @Column(nullable = false, length = 30)
    private String origem;

    @Column(length = 500)
    private String observacao;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;
}