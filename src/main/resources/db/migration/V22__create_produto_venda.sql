CREATE TABLE produto_venda (
    id BIGSERIAL PRIMARY KEY,

    produto_id BIGINT NOT NULL UNIQUE,

    preco_venda NUMERIC(10,2),

    imagem VARCHAR(500),

    disponivel_venda BOOLEAN NOT NULL DEFAULT TRUE,

    CONSTRAINT fk_produto_venda_produto
        FOREIGN KEY (produto_id)
        REFERENCES produtos(id)
);