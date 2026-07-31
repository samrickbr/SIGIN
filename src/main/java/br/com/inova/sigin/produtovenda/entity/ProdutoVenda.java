package br.com.inova.sigin.produtovenda.entity;

import br.com.inova.sigin.canalvenda.entity.CanalVenda;
import br.com.inova.sigin.produto.entity.Produto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produto_venda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "canal_venda_id", nullable = false)
    private CanalVenda canalVenda;


    @Column(
            name = "preco_venda",
            precision = 10,
            scale = 2
    )
    private BigDecimal precoVenda;


    @Column(length = 500)
    private String imagem;


    @Column(
            name = "disponivel_venda",
            nullable = false
    )
    private Boolean disponivelVenda = true;

}