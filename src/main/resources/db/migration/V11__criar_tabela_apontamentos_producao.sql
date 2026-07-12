CREATE TABLE apontamentos_producao (

    id BIGSERIAL PRIMARY KEY,

    ordem_producao_id BIGINT NOT NULL,

    quantidade_produzida NUMERIC(12,3) NOT NULL DEFAULT 0,

    quantidade_perda NUMERIC(12,3) NOT NULL DEFAULT 0,

    observacao VARCHAR(500),

    responsavel_id BIGINT,

    data_apontamento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    ativo BOOLEAN NOT NULL DEFAULT TRUE,


    CONSTRAINT fk_apontamento_op
        FOREIGN KEY (ordem_producao_id)
        REFERENCES ordens_producao(id),


    CONSTRAINT fk_apontamento_responsavel
        FOREIGN KEY (responsavel_id)
        REFERENCES pessoas(id)
);