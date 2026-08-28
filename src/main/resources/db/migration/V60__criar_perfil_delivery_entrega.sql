INSERT INTO perfis (
    nome,
    descricao,
    ativo
)
VALUES (
    'DELIVERY_ENTREGA',
    'Perfil de acesso à operação de Entrega do Delivery.',
    TRUE
)
ON CONFLICT (nome) DO NOTHING;