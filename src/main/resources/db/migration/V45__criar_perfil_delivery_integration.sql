INSERT INTO perfis (
    nome,
    descricao,
    ativo
)
SELECT
    'DELIVERY_INTEGRATION',
    'Integração técnica do Delivery',
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM perfis
    WHERE nome = 'DELIVERY_INTEGRATION'
);