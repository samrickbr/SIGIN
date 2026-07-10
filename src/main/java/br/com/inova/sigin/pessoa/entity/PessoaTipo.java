package br.com.inova.sigin.pessoa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "pessoa_tipos",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_pessoa_tipo",
                        columnNames = {"pessoa_id", "tipo_pessoa_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "pessoa_id",
            nullable = false
    )
    private Pessoa pessoa;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "tipo_pessoa_id",
            nullable = false
    )
    private TipoPessoa tipoPessoa;


    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
}