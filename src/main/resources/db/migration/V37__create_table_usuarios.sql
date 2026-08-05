CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    pessoa_id BIGINT NOT NULL,
    login VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    ultimo_login TIMESTAMP,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_usuarios_pessoas
        FOREIGN KEY (pessoa_id)
        REFERENCES pessoas(id),

    CONSTRAINT uk_usuarios_pessoa
        UNIQUE (pessoa_id)
);