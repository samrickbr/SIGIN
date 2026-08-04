-- ==========================================================
-- SIGIN
-- Sprint 05 - Financeiro Inicial
--
-- Ajusta pedidos criados antes da introdução dos campos
-- Canal de Venda e Forma de Pagamento.
--
-- Regras:
-- Canal padrão............. Loja Física (ID = 2)
-- Forma de pagamento....... PIX (ID = 1)
-- ==========================================================

UPDATE pedidos
SET canal_venda_id = 2
WHERE canal_venda_id IS NULL;

UPDATE pedidos
SET forma_pagamento_id = 1
WHERE forma_pagamento_id IS NULL;

ALTER TABLE pedidos
ALTER COLUMN canal_venda_id SET NOT NULL;

ALTER TABLE pedidos
ALTER COLUMN forma_pagamento_id SET NOT NULL;