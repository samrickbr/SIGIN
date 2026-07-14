package br.com.inova.sigin.pedido.entity;

import br.com.inova.sigin.produto.entity.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pedido_itens")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;


    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;


    @Column(nullable = false)
    private BigDecimal quantidade;


    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;


    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;


    @Column(nullable = false)
    private Boolean ativo;
}