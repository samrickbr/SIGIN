CREATE TABLE configuracoes_sistema (

    id BIGINT PRIMARY KEY,

    proximo_numero_pedido BIGINT NOT NULL,

    proximo_numero_op BIGINT NOT NULL,

    local_producao_padrao_id BIGINT,

    CONSTRAINT fk_config_local
        FOREIGN KEY (local_producao_padrao_id)
        REFERENCES locais(id)
);

INSERT INTO configuracoes_sistema
(id, proximo_numero_pedido, proximo_numero_op, local_producao_padrao_id)

VALUES
(1, 3, 3, 1);