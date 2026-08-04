UPDATE caixa_movimentos
SET origem = 'AJUSTE'
WHERE origem IS NULL;

ALTER TABLE caixa_movimentos
ALTER COLUMN origem SET NOT NULL;