package br.com.inova.sigin.produto.entity;

import br.com.inova.sigin.produto.enums.Setor;
import br.com.inova.sigin.produtovenda.entity.ProdutoVenda;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "preco_venda", precision = 10, scale = 2)
    private BigDecimal precoVenda;

    @Builder.Default
    @Column(name = "disponivel_venda", nullable = false)
    private Boolean disponivelVenda = true;

    @OneToMany(
            mappedBy = "produto",
            fetch = FetchType.LAZY
    )
    private List<ProdutoVenda> vendas;

    @Column(length = 500)
    private String imagem;

    @Builder.Default
    @Column(nullable = false)
    private Boolean ativo = true;

    @Builder.Default
    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "setor")
    private Setor setor;
}