ALTER TABLE ordens_producao
ADD COLUMN pedido_id BIGINT;

ALTER TABLE ordens_producao
ADD CONSTRAINT fk_ordem_pedido
FOREIGN KEY (pedido_id)
REFERENCES pedidos(id);