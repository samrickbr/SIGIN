package br.com.inova.sigin.pessoa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "pessoa_enderecos"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PessoaEndereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "pessoa_id",
            nullable = false
    )
    private Pessoa pessoa;

    @Column(nullable = false, length = 9)
    private String cep;

    @Column(nullable = false, length = 200)
    private String logradouro;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @Column(nullable = false, length = 100)
    private String bairro;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String uf;

    @Column(nullable = false)
    @Builder.Default
    private Boolean principal = false;
}