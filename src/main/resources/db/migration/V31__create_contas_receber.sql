CREATE TABLE contas_receber (
    id BIGSERIAL PRIMARY KEY,

    pedido_id BIGINT NOT NULL,
    pessoa_id BIGINT NOT NULL,
    forma_pagamento_id BIGINT NOT NULL,

    valor NUMERIC(10,2) NOT NULL,

    data_vencimento DATE NOT NULL,

    status VARCHAR(30) NOT NULL,

    observacao VARCHAR(255),

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_conta_receber_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedidos(id),

    CONSTRAINT fk_conta_receber_pessoa
        FOREIGN KEY (pessoa_id)
        REFERENCES pessoas(id),

    CONSTRAINT fk_conta_receber_forma_pagamento
        FOREIGN KEY (forma_pagamento_id)
        REFERENCES formas_pagamento(id)
);