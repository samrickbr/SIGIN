CREATE TABLE compras (
    id BIGSERIAL PRIMARY KEY,

    fornecedor_id BIGINT,

    numero VARCHAR(50) NOT NULL UNIQUE,

    data_compra TIMESTAMP NOT NULL,

    status VARCHAR(30) NOT NULL,

    observacao VARCHAR(500),

    ativo BOOLEAN NOT NULL,

    data_criacao TIMESTAMP NOT NULL,

    CONSTRAINT fk_compra_fornecedor
        FOREIGN KEY (fornecedor_id)
        REFERENCES pessoas(id)
);