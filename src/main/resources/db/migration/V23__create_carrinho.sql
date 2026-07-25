CREATE TABLE carrinhos (
    id BIGSERIAL PRIMARY KEY,

    cliente_id BIGINT NOT NULL,

    status VARCHAR(20) NOT NULL,

    data_criacao TIMESTAMP NOT NULL,

    CONSTRAINT fk_carrinho_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES pessoas(id)
);


CREATE TABLE carrinho_itens (
    id BIGSERIAL PRIMARY KEY,

    carrinho_id BIGINT NOT NULL,

    produto_venda_id BIGINT NOT NULL,

    quantidade NUMERIC(10,2) NOT NULL,

    valor_unitario NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_carrinho_item_carrinho
        FOREIGN KEY (carrinho_id)
        REFERENCES carrinhos(id),

    CONSTRAINT fk_carrinho_item_produto_venda
        FOREIGN KEY (produto_venda_id)
        REFERENCES produto_venda(id)
);