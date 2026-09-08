-- Permissão administrativa para reparar referências
-- de PedidoItem do Delivery Back para itens do SIGIN Core.

INSERT INTO permissoes (
    codigo,
    descricao,
    ativo
)
SELECT
    'PEDIDO_ITEM_REPARAR_CORE',
    'Permite reparar referências de itens do pedido no SIGIN Core',
    TRUE
WHERE NOT EXISTS (
    SELECT 1
    FROM permissoes
    WHERE codigo = 'PEDIDO_ITEM_REPARAR_CORE'
);

-- Perfil Administrador recebe a permissão.
INSERT INTO perfil_permissoes (
    perfil_id,
    permissao_id
)
SELECT
    perfil.id,
    permissao.id
FROM perfis perfil
CROSS JOIN permissoes permissao
WHERE perfil.nome = 'Administrador'
  AND permissao.codigo = 'PEDIDO_ITEM_REPARAR_CORE'
  AND NOT EXISTS (
      SELECT 1
      FROM perfil_permissoes pp
      WHERE pp.perfil_id = perfil.id
        AND pp.permissao_id = permissao.id
  );