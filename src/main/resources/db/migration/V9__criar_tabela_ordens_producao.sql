CREATE TABLE ordens_producao (

    id BIGSERIAL PRIMARY KEY,

    numero VARCHAR(20) NOT NULL UNIQUE,

    produto_id BIGINT NOT NULL,

    quantidade_planejada NUMERIC(12,3) NOT NULL,

    quantidade_produzida NUMERIC(12,3) NOT NULL DEFAULT 0,

    local_destino_id BIGINT NOT NULL,

    responsavel_id BIGINT,

    status VARCHAR(30) NOT NULL,

    origem VARCHAR(30) NOT NULL,

    observacao VARCHAR(500),

    ativo BOOLEAN NOT NULL DEFAULT TRUE,

    data_abertura TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    data_inicio TIMESTAMP,

    data_fim TIMESTAMP,

    CONSTRAINT fk_op_produto
        FOREIGN KEY (produto_id)
        REFERENCES produtos(id),

    CONSTRAINT fk_op_local
        FOREIGN KEY (local_destino_id)
        REFERENCES locais(id),

    CONSTRAINT fk_op_responsavel
        FOREIGN KEY (responsavel_id)
        REFERENCES pessoas(id)
);