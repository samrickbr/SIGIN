package br.com.inova.sigin.pedido.entity;

import br.com.inova.sigin.pedido.enums.StatusPedido;
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

    @Column(nullable = false)
    private LocalDateTime dataPedido;

    @OneToMany(mappedBy = "pedido")
    @Builder.Default
    private List<PedidoItem> itens = new ArrayList<>();

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedido status;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(length = 500)
    private String observacao;
}
