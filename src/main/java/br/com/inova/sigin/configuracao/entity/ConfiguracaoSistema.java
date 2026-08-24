package br.com.inova.sigin.configuracao.entity;

import br.com.inova.sigin.local.entity.Local;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "configuracoes_sistema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracaoSistema {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long proximoNumeroPedido;

    @Column(nullable = false)
    private Long proximoNumeroOp;

    @ManyToOne
    @JoinColumn(name = "local_producao_padrao_id")
    private Local localProducaoPadrao;

    @Column(
            name = "taxa_entrega_padrao",
            nullable = false,
            precision = 12,
            scale = 2
    )
    private BigDecimal taxaEntregaPadrao;
}