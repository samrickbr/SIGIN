package br.com.inova.sigin.usuario.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "usuarios_perfis",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"usuario_id", "perfil_id"})
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioPerfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;
}