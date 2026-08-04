ALTER TABLE pedidos
ADD COLUMN forma_pagamento_id BIGINT;

ALTER TABLE pedidos
ADD CONSTRAINT fk_pedido_forma_pagamento
FOREIGN KEY (forma_pagamento_id)
REFERENCES formas_pagamento(id);