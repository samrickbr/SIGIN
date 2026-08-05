package br.com.inova.sigin.usuario.entity;

import br.com.inova.sigin.pessoa.entity.Pessoa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pessoa_id", nullable = false, unique = true)
    private Pessoa pessoa;

    @Column(nullable = false, unique = true, length = 150)
    private String login;

    @Column(nullable = false, length = 255)
    private String senha;

    @Builder.Default
    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Builder.Default
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
}