package br.com.inova.sigin.movimentacaoestoque.entity;

import br.com.inova.sigin.local.entity.Local;
import br.com.inova.sigin.material.entity.Material;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.produto.entity.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes_estoque")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id")
    private Produto produto;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id")
    private Material material;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_id", nullable = false)
    private Local local;


    @Column(nullable = false, length = 50)
    private String tipo;


    @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal quantidade;


    @Column(nullable = false, length = 50)
    private String origem;


    @Column(name = "referencia_id")
    private Long referenciaId;


    @Column(length = 500)
    private String observacao;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id")
    private Pessoa responsavel;


    @Column(name = "data_movimentacao", nullable = false)
    private LocalDateTime dataMovimentacao;


    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;
}