CREATE TABLE pedidos_pagamentos (
    id BIGSERIAL PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    forma_pagamento_id BIGINT NOT NULL,
    valor NUMERIC(12,2) NOT NULL,

    CONSTRAINT fk_pedidos_pagamentos_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedidos(id),

    CONSTRAINT fk_pedidos_pagamentos_forma_pagamento
        FOREIGN KEY (forma_pagamento_id)
        REFERENCES formas_pagamento(id),

    CONSTRAINT ck_pedidos_pagamentos_valor
        CHECK (valor > 0)
);