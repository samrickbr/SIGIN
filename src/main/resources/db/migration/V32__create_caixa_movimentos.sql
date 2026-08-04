CREATE TABLE caixa_movimentos (
    id BIGSERIAL PRIMARY KEY,

    tipo VARCHAR(20) NOT NULL,

    valor NUMERIC(10,2) NOT NULL,

    data_movimento TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    origem VARCHAR(50),

    referencia_id BIGINT,

    observacao VARCHAR(255)
);