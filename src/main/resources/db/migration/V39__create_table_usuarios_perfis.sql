CREATE TABLE usuarios_perfis (

    id BIGSERIAL PRIMARY KEY,

    usuario_id BIGINT NOT NULL,

    perfil_id BIGINT NOT NULL,

    CONSTRAINT fk_usuario_perfil_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuarios(id),

    CONSTRAINT fk_usuario_perfil_perfil
        FOREIGN KEY (perfil_id)
        REFERENCES perfis(id),

    CONSTRAINT uk_usuario_perfil
        UNIQUE (usuario_id, perfil_id)
);