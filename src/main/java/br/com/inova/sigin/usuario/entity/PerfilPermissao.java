package br.com.inova.sigin.usuario.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "perfil_permissoes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_perfil_permissao",
                        columnNames = {"perfil_id", "permissao_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerfilPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "perfil_id",
            nullable = false
    )
    private Perfil perfil;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "permissao_id",
            nullable = false
    )
    private Permissao permissao;
}