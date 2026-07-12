package br.com.inova.sigin.apontamentoproducao.entity;

import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "apontamentos_producao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApontamentoProducao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_producao_id", nullable = false)
    private OrdemProducao ordemProducao;


    @Column(
            name = "quantidade_produzida",
            nullable = false,
            precision = 12,
            scale = 3
    )
    @Builder.Default
    private BigDecimal quantidadeProduzida = BigDecimal.ZERO;


    @Column(
            name = "quantidade_perda",
            nullable = false,
            precision = 12,
            scale = 3
    )
    @Builder.Default
    private BigDecimal quantidadePerda = BigDecimal.ZERO;


    @Column(length = 500)
    private String observacao;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private Pessoa responsavel;


    @Column(name = "data_apontamento", nullable = false)
    private LocalDateTime dataApontamento;


    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;
}