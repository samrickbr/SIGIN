CREATE TABLE pedido_enderecos (
    id BIGSERIAL PRIMARY KEY,

    pedido_id BIGINT NOT NULL,
    pessoa_endereco_id BIGINT,

    cep VARCHAR(9) NOT NULL,
    logradouro VARCHAR(200) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL,

    CONSTRAINT uk_pedido_endereco_pedido
        UNIQUE (pedido_id),

    CONSTRAINT fk_pedido_endereco_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedidos(id)
);