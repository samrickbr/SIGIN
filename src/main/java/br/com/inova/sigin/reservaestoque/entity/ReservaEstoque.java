package br.com.inova.sigin.reservaestoque.entity;

import br.com.inova.sigin.material.entity.Material;
import br.com.inova.sigin.ordemproducao.entity.OrdemProducao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas_estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ordem_producao_id", nullable = false)
    private OrdemProducao ordemProducao;

    @Column(nullable = false, precision = 19, scale = 3)
    private BigDecimal quantidade;

    @Column(nullable = false)
    private LocalDateTime dataReserva;

    @Builder.Default
    @Column(nullable = false)
    private Boolean ativo = true;
}