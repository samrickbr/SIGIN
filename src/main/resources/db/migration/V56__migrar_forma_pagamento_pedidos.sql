-- ==========================================================
-- SIGIN
-- Migração da forma de pagamento legada dos pedidos
--
-- A partir da V50, os pagamentos passam a ser representados
-- pela tabela pedidos_pagamentos.
--
-- A coluna pedidos.forma_pagamento_id é legado da V34/V36.
--
-- Pedidos com valor positivo:
--   migram para pedidos_pagamentos.
--
-- Pedidos com valor zero:
--   não recebem registro em pedidos_pagamentos, pois a
--   tabela exige valor > 0.
-- ==========================================================

INSERT INTO pedidos_pagamentos (
    pedido_id,
    forma_pagamento_id,
    valor
)
SELECT
    p.id,
    p.forma_pagamento_id,
    p.valor_total
FROM pedidos p
WHERE p.forma_pagamento_id IS NOT NULL
  AND p.valor_total > 0
  AND NOT EXISTS (
      SELECT 1
      FROM pedidos_pagamentos pp
      WHERE pp.pedido_id = p.id
  );

ALTER TABLE pedidos
DROP CONSTRAINT IF EXISTS fk_pedido_forma_pagamento;

ALTER TABLE pedidos
DROP COLUMN forma_pagamento_id;