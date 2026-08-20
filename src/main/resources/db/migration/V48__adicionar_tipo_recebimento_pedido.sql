ALTER TABLE pedidos
    ADD COLUMN tipo_recebimento VARCHAR(20);

UPDATE pedidos
SET tipo_recebimento = 'ENTREGA'
WHERE tipo_recebimento IS NULL;

ALTER TABLE pedidos
    ALTER COLUMN tipo_recebimento SET NOT NULL;
