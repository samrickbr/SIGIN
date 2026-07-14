CREATE TABLE pedido_itens
(
    id BIGSERIAL PRIMARY KEY,

    pedido_id BIGINT NOT NULL,

    produto_id BIGINT NOT NULL,

    quantidade NUMERIC(19,3) NOT NULL,

    valor_unitario NUMERIC(19,2) NOT NULL DEFAULT 0,

    valor_total NUMERIC(19,2) NOT NULL DEFAULT 0,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,


    CONSTRAINT fk_pedido_item_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedidos(id),


    CONSTRAINT fk_pedido_item_produto
        FOREIGN KEY (produto_id)
        REFERENCES produtos(id)
);