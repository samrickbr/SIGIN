CREATE TABLE pessoas (
    id BIGSERIAL PRIMARY KEY,

    nome VARCHAR(150) NOT NULL,

    tipo_documento VARCHAR(20),
    documento VARCHAR(30),

    telefone VARCHAR(20),
    email VARCHAR(150),

    observacao VARCHAR(500),

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE UNIQUE INDEX idx_pessoa_documento
ON pessoas(documento)
WHERE documento IS NOT NULL;


CREATE TABLE tipos_pessoa (
    id BIGSERIAL PRIMARY KEY,

    nome VARCHAR(50) NOT NULL UNIQUE,

    descricao VARCHAR(255),

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE pessoa_tipos (
    id BIGSERIAL PRIMARY KEY,

    pessoa_id BIGINT NOT NULL,

    tipo_pessoa_id BIGINT NOT NULL,

    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT fk_pessoa_tipo_pessoa
        FOREIGN KEY (pessoa_id)
        REFERENCES pessoas(id),


    CONSTRAINT fk_pessoa_tipo_tipo
        FOREIGN KEY (tipo_pessoa_id)
        REFERENCES tipos_pessoa(id),


    CONSTRAINT uk_pessoa_tipo
        UNIQUE (pessoa_id, tipo_pessoa_id)
);