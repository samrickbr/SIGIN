CREATE TABLE perfis (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT uk_perfis_nome UNIQUE (nome)
);