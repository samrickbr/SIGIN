ALTER TABLE produto_venda
ADD COLUMN canal_venda_id BIGINT;

ALTER TABLE produto_venda
ADD CONSTRAINT fk_produto_venda_canal_venda
FOREIGN KEY (canal_venda_id)
REFERENCES canais_venda(id);

ALTER TABLE produto_venda
DROP CONSTRAINT produto_venda_produto_id_key;

ALTER TABLE produto_venda
ADD CONSTRAINT uk_produto_venda_produto_canal
UNIQUE (produto_id, canal_venda_id);