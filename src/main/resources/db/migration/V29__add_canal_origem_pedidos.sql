ALTER TABLE pedidos
ADD COLUMN canal_venda_id BIGINT;

ALTER TABLE pedidos
ADD CONSTRAINT fk_pedido_canal_venda
FOREIGN KEY (canal_venda_id)
REFERENCES canais_venda(id);