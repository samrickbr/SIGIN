package br.com.inova.sigin.opmaterial.entity;

import br.com.inova.sigin.material.entity.Material;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "op_materiais",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_op_material",
                        columnNames = {"ordem_producao_id", "material_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_producao_id", nullable = false)
    private OrdemProducao ordemProducao;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;


    @Column(name = "quantidade_planejada", nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidadePlanejada;


    @Column(name = "quantidade_consumida", nullable = false, precision = 12, scale = 3)
    @Builder.Default
    private BigDecimal quantidadeConsumida = BigDecimal.ZERO;


    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;


    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
}