CREATE TABLE pedidos
(
    id BIGSERIAL PRIMARY KEY,

    numero VARCHAR(20) NOT NULL UNIQUE,

    cliente_id BIGINT NOT NULL,

    data_pedido TIMESTAMP NOT NULL,

    valor_total NUMERIC(19,2) NOT NULL DEFAULT 0,

    status VARCHAR(30) NOT NULL,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    observacao VARCHAR(500),

    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES pessoas (id)
);