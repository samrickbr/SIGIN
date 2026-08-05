package br.com.inova.sigin.usuario.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "perfis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Builder.Default
    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToMany(mappedBy = "perfil", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<PerfilPermissao> permissoes = new HashSet<>();

}