package br.com.inova.sigin.pessoa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pessoas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(name = "tipo_documento", length = 20)
    private String tipoDocumento;

    @Column(length = 30)
    private String documento;

    @Column(length = 20)
    private String telefone;

    @Column(length = 150)
    private String email;

    @Column(length = 500)
    private String observacao;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @OneToMany(mappedBy = "pessoa")
    @Builder.Default
    private Set<PessoaTipo> tipos = new HashSet<>();
}