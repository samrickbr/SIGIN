ALTER TABLE produtos
    ADD COLUMN setor VARCHAR(50);

ALTER TABLE pedido_itens
    ADD COLUMN setor VARCHAR(50);