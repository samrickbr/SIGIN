package br.com.inova.sigin.financeiro.entity;

import br.com.inova.sigin.financeiro.enums.OrigemMovimentoCaixa;
import br.com.inova.sigin.financeiro.enums.TipoMovimentoCaixa;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "caixa_movimentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaixaMovimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimentoCaixa tipo;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private LocalDateTime dataMovimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrigemMovimentoCaixa origem;

    private Long referenciaId;

    private String observacao;

    @PrePersist
    public void prePersist(){

        if(dataMovimento == null){
            dataMovimento = LocalDateTime.now();
        }
    }
}