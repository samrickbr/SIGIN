package br.com.inova.sigin.api.delivery.carrinho.entity;

import br.com.inova.sigin.pessoa.entity.Pessoa;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrinhos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Pessoa cliente;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarrinhoStatus status;


    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;


    @OneToMany(
            mappedBy = "carrinho",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Builder.Default
    private List<CarrinhoItem> itens = new ArrayList<>();

    @Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

}