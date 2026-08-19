CREATE TABLE pessoa_enderecos (
    id BIGSERIAL PRIMARY KEY,

    pessoa_id BIGINT NOT NULL,

    cep VARCHAR(9) NOT NULL,
    logradouro VARCHAR(200) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,

    principal BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_pessoa_endereco_pessoa
        FOREIGN KEY (pessoa_id)
        REFERENCES pessoas(id)
);

CREATE INDEX idx_pessoa_enderecos_pessoa
    ON pessoa_enderecos (pessoa_id);

CREATE UNIQUE INDEX uk_pessoa_endereco_principal
    ON pessoa_enderecos (pessoa_id)
    WHERE principal = TRUE;