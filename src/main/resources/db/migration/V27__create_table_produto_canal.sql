CREATE TABLE produtos_canais (
    id BIGSERIAL PRIMARY KEY,

    produto_id BIGINT NOT NULL,
    canal_venda_id BIGINT NOT NULL,

    preco_venda NUMERIC(15,2),
    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_produtos_canais_produto
        FOREIGN KEY (produto_id)
        REFERENCES produtos(id),

    CONSTRAINT fk_produtos_canais_canal_venda
        FOREIGN KEY (canal_venda_id)
        REFERENCES canais_venda(id),

    CONSTRAINT uk_produtos_canais
        UNIQUE (produto_id, canal_venda_id)
);