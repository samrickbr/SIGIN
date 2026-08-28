INSERT INTO perfis (
    nome,
    descricao,
    ativo
)
VALUES
(
    'DELIVERY_COZINHA',
    'Perfil de acesso à tela operacional de Cozinha do Delivery.',
    TRUE
),
(
    'DELIVERY_PIZZARIA',
    'Perfil de acesso à tela operacional de Pizzaria do Delivery.',
    TRUE
),
(
    'DELIVERY_BALCAO',
    'Perfil de acesso à tela operacional de Balcão do Delivery.',
    TRUE
),
(
    'DELIVERY_MINIPDV',
    'Perfil de acesso à tela operacional do Mini PDV do Delivery.',
    TRUE
)
ON CONFLICT (nome) DO NOTHING;