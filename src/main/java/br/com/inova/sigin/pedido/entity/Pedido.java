package br.com.inova.sigin.pedido.entity;

import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import br.com.inova.sigin.financeiro.entity.FormaPagamento;
import br.com.inova.sigin.pedido.enums.StatusPedido;
import br.com.inova.sigin.pedido.enums.TipoRecebimento;
import br.com.inova.sigin.pessoa.entity.Pessoa;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String numero;

    @ManyToOne(optional = false)
    private Pessoa cliente;

    @OneToOne(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private PedidoEndereco endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_recebimento", nullable = false, length = 20)
    private TipoRecebimento tipoRecebimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "canal_venda_id")
    private CanalVenda canalVenda;

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<PedidoItem> itens = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(name = "taxa_entrega", nullable = false, precision = 12, scale = 2)
    private BigDecimal taxaEntrega;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pagamento_id")
    private FormaPagamento formaPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(length = 500)
    private String observacao;
}
