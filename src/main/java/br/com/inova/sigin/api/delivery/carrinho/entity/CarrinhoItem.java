package br.com.inova.sigin.api.delivery.carrinho.entity;

import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "carrinho_itens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarrinhoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carrinho_id", nullable = false)
    private Carrinho carrinho;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_venda_id", nullable = false)
    private ProdutoVenda produtoVenda;


    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantidade;


    @Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal;

}