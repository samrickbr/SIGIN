CREATE TABLE perfil_permissoes (
    id BIGSERIAL PRIMARY KEY,

    perfil_id BIGINT NOT NULL,
    permissao_id BIGINT NOT NULL,

    CONSTRAINT fk_perfil_permissoes_perfil
        FOREIGN KEY (perfil_id)
        REFERENCES perfis(id),

    CONSTRAINT fk_perfil_permissoes_permissao
        FOREIGN KEY (permissao_id)
        REFERENCES permissoes(id),

    CONSTRAINT uk_perfil_permissao
        UNIQUE (perfil_id, permissao_id)
);