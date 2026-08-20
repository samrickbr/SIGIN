ALTER TABLE pedidos
    ADD COLUMN taxa_entrega NUMERIC(12,2);

UPDATE pedidos
SET taxa_entrega = 0
WHERE taxa_entrega IS NULL;

ALTER TABLE pedidos
    ALTER COLUMN taxa_entrega SET NOT NULL;