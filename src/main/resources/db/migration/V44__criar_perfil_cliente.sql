-- V44__criar_perfil_cliente.sql

INSERT INTO perfis (
    nome,
    descricao,
    ativo
)
SELECT
    'CLIENTE',
    'Cliente do Delivery',
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM perfis
    WHERE nome = 'CLIENTE'
);