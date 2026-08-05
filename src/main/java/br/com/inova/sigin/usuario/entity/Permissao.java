package br.com.inova.sigin.usuario.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String codigo;

    @Column(length = 255)
    private String descricao;

    @Builder.Default
    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToMany(
            mappedBy = "permissao",
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<PerfilPermissao> perfis = new HashSet<>();
}